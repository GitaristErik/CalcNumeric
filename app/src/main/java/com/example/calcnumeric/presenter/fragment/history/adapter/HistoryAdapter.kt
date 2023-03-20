package com.example.calcnumeric.presenter.fragment.history.adapter

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnCreateContextMenuListener
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calcnumeric.R
import com.example.calcnumeric.databinding.FragmentHistoryItemBinding
import com.example.calcnumeric.databinding.FragmentHistoryItemHeaderBinding
import com.example.calcnumeric.presenter.model.HistoryUiModel
import com.example.calcnumeric.utils.DateFormatter

class HistoryAdapter(
    private val itemClickListener: HistoryItemClickListener
) : ListAdapter<HistoryUiModel, RecyclerView.ViewHolder>(HistoryDiffCallback) {

    enum class ViewHolderType {
        HEADER, CONTENT
    }

    fun getHistoryFromGroupId(position: Int) =
        (currentList[position] as HistoryUiModel.ContentModel)
            .mapToHistory()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)

        return when (ViewHolderType.values()[viewType]) {
            ViewHolderType.HEADER -> HeaderViewHolder(
                FragmentHistoryItemHeaderBinding.inflate(inflater, parent, false)
            )

            ViewHolderType.CONTENT -> ContentViewHolder(
                FragmentHistoryItemBinding.inflate(inflater, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is ContentViewHolder -> holder.bind(item as? HistoryUiModel.ContentModel)
            is HeaderViewHolder -> holder.bind(item as? HistoryUiModel.HeaderModel)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position < 0 || position >= currentList.size)
            return ViewHolderType.HEADER.ordinal

        return when (getItem(position)) {
            is HistoryUiModel.HeaderModel -> ViewHolderType.HEADER
            is HistoryUiModel.ContentModel -> ViewHolderType.CONTENT
            null -> ViewHolderType.CONTENT
        }.ordinal
    }

    inner class ContentViewHolder(private val binding: FragmentHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root),
        OnClickListener, OnCreateContextMenuListener {

        fun bind(history: HistoryUiModel.ContentModel?) = history?.let {
            binding.run {
                viewMainContainer.visibility = View.VISIBLE
                expression.text = history.expression
                answer.text = history.result

                root.setOnCreateContextMenuListener(this@ContentViewHolder)
            }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu.add(
                bindingAdapterPosition,
                Menu.NONE,
                Menu.NONE,
                R.string.history_item_menu_delete_button
            )
        }

        override fun onClick(view: View) {
            getItem(bindingAdapterPosition)?.let {
                itemClickListener.onItemClicked((it as HistoryUiModel.ContentModel).mapToHistory())
            }
        }

        init {
            binding.run {
                listOf<View>(
                    root,
                    answer,
                    expression,
                    viewMain.rootView,
                    viewMainContainer.rootView,
                ).forEach {
                    it.setOnClickListener(this@ContentViewHolder)
                }
            }
        }
    }

    inner class HeaderViewHolder(
        private val binding: FragmentHistoryItemHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(header: HistoryUiModel.HeaderModel?) = header?.let {
            binding.run {
                dateHeader.apply {
                    visibility = View.VISIBLE
                    text = with(DateFormatter) {
                        if (!isToday(it.date)) {
                            formatDate(it.date)
                        } else resources.getString(
                            R.string.history_item_header_today
                        )
                    }
                }
            }
        }
    }
}
