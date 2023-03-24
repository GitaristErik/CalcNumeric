package com.example.calcnumeric.presenter.fragment.history

import androidx.lifecycle.viewModelScope
import com.example.calcnumeric.domain.entity.History
import com.example.calcnumeric.domain.entity.Results
import com.example.calcnumeric.domain.helper.DispatcherProvider
import com.example.calcnumeric.domain.usecase.ClearHistoryUseCase
import com.example.calcnumeric.domain.usecase.DeleteHistoryByExpressionUseCase
import com.example.calcnumeric.domain.usecase.GetHistoryAllUseCase
import com.example.calcnumeric.domain.usecase.InsertHistoryUseCase
import com.example.calcnumeric.presenter.BaseViewModel
import com.example.calcnumeric.presenter.fragment.history.HistoryViewModel.ViewData
import com.example.calcnumeric.presenter.model.HistoryUiModel
import com.example.calcnumeric.presenter.utils.DateFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryListUseCase: GetHistoryAllUseCase,
    private val deleteHistoryByExpressionUseCase: DeleteHistoryByExpressionUseCase,
    private val clearHistoryUseCase: ClearHistoryUseCase,
    private val insertHistoryUseCase: InsertHistoryUseCase,
    private val dispatcher: DispatcherProvider
) : BaseViewModel<ViewData>(ViewData()) {

    init {
        log.d("init")
        fetch()
    }

    private fun fetch() {
        viewModelScope.launch(dispatcher.io()) {
            getHistoryListUseCase().collect { result ->
                when (result) {
                    is Results.Success -> {
                        val historyUiModels = transformHistoryUiModels(result.data)
                        _data.update {
                            it.copy(uiData = Results.Success(historyUiModels))
                        }
                    }

                    is Results.Loading -> {
                        val historyUiModels = result.oldData?.let {
                            transformHistoryUiModels(it)
                        }
                        _data.update { it.copy(uiData = Results.Loading(historyUiModels)) }
                    }

                    is Results.Failure -> {
                        val historyUiModels = result.oldData?.let {
                            transformHistoryUiModels(it)
                        }

                        val uiData = Results.Failure(
                            result.throwable,
                            result.type,
                            historyUiModels
                        )

                        _data.update { it.copy(uiData = uiData) }
                    }
                }
            }
        }
    }

    fun clearHistory() = safeRunJob(dispatcher.default()) {
        log.d("call")
        clearHistoryUseCase().let {
            when (it) {
                is Results.Success -> log.d("clear success")
                is Results.Failure -> log.d("clear failed: ${it.throwable.message}")
                is Results.Loading -> log.d("clear loading")
            }
        }
    }

    fun deleteHistoryByExpression(expression: String) = safeRunJob(dispatcher.default()) {
        log.d("call with id: $expression")
        deleteHistoryByExpressionUseCase(expression).let {
            when (it) {
                is Results.Success -> log.d("delete success")
                is Results.Failure -> log.d("delete failed: ${it.throwable.message}")
                is Results.Loading -> log.d("delete loading")
            }
        }
    }

    fun insertHistory(history: History) = safeRunJob(dispatcher.default()) {
        log.d("call with history: $history")
        insertHistoryUseCase(history).let {
            when (it) {
                is Results.Success -> log.d("insert success")
                is Results.Failure -> log.d("insert failed: ${it.throwable.message}")
                is Results.Loading -> log.d("insert loading")
            }
        }
    }

    fun restoreHistory(history: History) {
        log.d("call with history: $history")
        insertHistory(history)
    }

    fun setClickedExpression(expression: String) {
        log.d("call with expression: $expression")
    }

    private fun transformHistoryUiModels(
        historyItems: List<History>
    ): List<HistoryUiModel> {
        val historyUiModels = ArrayList<HistoryUiModel>()
        var before: HistoryUiModel.ContentModel? = null
        for (history in historyItems) {
            val after = HistoryUiModel.ContentModel(history)
            if (isShouldHeader(before, after)) {
                historyUiModels.add(HistoryUiModel.HeaderModel(history.date))
            }
            historyUiModels.add(after)
            before = after
        }
        return historyUiModels
    }

    private fun isShouldHeader(
        before: HistoryUiModel.ContentModel?,
        after: HistoryUiModel?
    ): Boolean {
        if (before == null && after == null) {
            return false
        }

        return if (after is HistoryUiModel.ContentModel?) {
            val beforeTimestamp =
                DateFormatter.roundToDay(before?.date ?: return true)
            val afterTimestamp =
                DateFormatter.roundToDay(after?.date ?: return false)
            beforeTimestamp != afterTimestamp
        } else false
    }

    data class ViewData(
        val uiData: Results<List<HistoryUiModel>> = Results.Loading(emptyList()),
    )
}