package com.example.calcnumeric.presenter.model

import com.example.calcnumeric.domain.entity.History

sealed class HistoryUiModel {
    data class ContentModel(
        val date: Long,
        val expression: String,
        val result: String,
    ) : HistoryUiModel() {
        constructor(history: History) : this(
            history.date,
            history.expression,
            history.result
        )

        fun mapToHistory() = History(expression, result, date)
    }

    data class HeaderModel(val date: Long) : HistoryUiModel()
}
