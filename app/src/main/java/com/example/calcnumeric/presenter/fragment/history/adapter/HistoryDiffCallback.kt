package com.example.calcnumeric.presenter.fragment.history.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.calcnumeric.presenter.model.HistoryUiModel
import com.example.calcnumeric.presenter.utils.DateFormatter

object HistoryDiffCallback : DiffUtil.ItemCallback<HistoryUiModel>() {
    override fun areItemsTheSame(oldItem: HistoryUiModel, newItem: HistoryUiModel): Boolean {
        val isSameContent = oldItem is HistoryUiModel.ContentModel
                && newItem is HistoryUiModel.ContentModel
                && oldItem.id == newItem.id

        val isSameHeader = oldItem is HistoryUiModel.HeaderModel
                && newItem is HistoryUiModel.HeaderModel
                && DateFormatter.roundToDay(oldItem.date) == DateFormatter.roundToDay(newItem.date)

        return isSameContent || isSameHeader
    }

    override fun areContentsTheSame(oldItem: HistoryUiModel, newItem: HistoryUiModel) =
        oldItem == newItem
}