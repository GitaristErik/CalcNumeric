package com.example.calcnumeric.presenter.fragment.history.adapter

import android.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class HistoryItemSwipeCallback(
    private val context: Context,
    private val adapter: HistoryAdapter,
    private val direction: Int,
) : ItemTouchHelper.Callback(),
    HistoryItemRemovable by adapter {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.bindingAdapterPosition
        val history = adapter.getHistoryFromId(position)
        onRemoved(history)
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return when (viewHolder.itemViewType) {
            HistoryAdapter.ViewHolderType.CONTENT.ordinal -> {
                makeMovementFlags(0, direction)
            }

            else -> {
                makeMovementFlags(0, 0)
            }
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView = viewHolder.itemView

        val isCancelled = dX == 0f && !isCurrentlyActive
        if (isCancelled) {
            clearCanvas(
                c,
                itemView.right + dX,
                itemView.top.toFloat(),
                itemView.right.toFloat(),
                itemView.bottom.toFloat()
            )
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, false)
            return
        }

        ColorDrawable().apply {
            color = ContextCompat.getColor(context, R.color.holo_red_dark)

            setBounds(
                itemView.left - if (dX > 0) dX.toInt() else 0,
                itemView.top,
                itemView.right - if (dX < 0) dX.toInt() else 0,
                itemView.bottom
            )
            draw(c)
        }

        ContextCompat.getDrawable(context, R.drawable.ic_menu_delete)?.apply {
            val itemHeight = itemView.height
            val deleteIconMargin = (itemHeight - intrinsicHeight) / 2

            val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
            val deleteIconLeft =
                if (dX < 0) itemView.right - deleteIconMargin - intrinsicWidth
                else itemView.left + deleteIconMargin
            val deleteIconRight =
                if (dX < 0) itemView.right - deleteIconMargin
                else itemView.left + deleteIconMargin + intrinsicWidth
            val deleteIconBottom = deleteIconTop + intrinsicHeight

            setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
            draw(c)
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(c: Canvas, left: Float, top: Float, right: Float, bottom: Float) {
        val clearPaint: Paint = Paint().apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        }
        return c.drawRect(left, top, right, bottom, clearPaint)
    }


    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.75f
    }
}
