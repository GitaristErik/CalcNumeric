package com.example.calcnumeric.presenter.fragment.history.adapter

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.example.calcnumeric.presenter.fragment.history.adapter.HistoryAdapter.ViewHolderType
import com.google.android.material.divider.MaterialDividerItemDecoration

class HistoryDividerItemDecoration(context: Context, attributeSet: AttributeSet?, orientation: Int) :
    MaterialDividerItemDecoration(context, attributeSet, orientation) {

    constructor(context: Context, orientation: Int) : this(context, null, orientation)

    override fun shouldDrawDivider(position: Int, adapter: RecyclerView.Adapter<*>?): Boolean {
        return adapter?.run {
            compareViewHolders(
                first = getItemViewType(position),
                second = getItemViewType(position + 1)
            )
        } ?: false
    }

    private fun compareViewHolders(first: Int, second: Int): Boolean {
        var (firstType, secondType) = arrayOf<ViewHolderType?>(null, null)
        ViewHolderType.values().forEach {
            if (it.ordinal == first) firstType = it
            if (it.ordinal == second) secondType = it
        }
        return firstType != null && secondType != null &&
                firstType == ViewHolderType.CONTENT &&
                secondType == ViewHolderType.CONTENT
    }

}