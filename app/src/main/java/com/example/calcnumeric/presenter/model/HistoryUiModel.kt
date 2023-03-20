package com.example.calcnumeric.presenter.model

import com.example.calcnumeric.domain.model.History

sealed class HistoryUiModel {
    data class ContentModel(
        val id: Int,
        val date: Long,
        val expression: String,
        val result: String,
    ) : HistoryUiModel() {
        constructor(history: History) : this(
            history.id,
            history.date,
            history.expression,
            history.result
        )

        fun mapToHistory() = History(id, date, expression, result)
    }

    data class HeaderModel(val date: Long) : HistoryUiModel()
}
