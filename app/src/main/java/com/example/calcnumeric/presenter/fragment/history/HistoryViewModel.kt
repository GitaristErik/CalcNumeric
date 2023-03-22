package com.example.calcnumeric.presenter.fragment.history

import com.example.calcnumeric.domain.entity.History
import com.example.calcnumeric.domain.entity.Results
import com.example.calcnumeric.domain.helper.DispatcherProvider
import com.example.calcnumeric.domain.usecase.ClearHistoryUseCase
import com.example.calcnumeric.domain.usecase.DeleteHistoryByIdUseCase
import com.example.calcnumeric.domain.usecase.GetHistoryAllUseCase
import com.example.calcnumeric.domain.usecase.RestoreHistoryUseCase
import com.example.calcnumeric.presenter.BaseViewModel
import com.example.calcnumeric.presenter.fragment.history.HistoryViewModel.ViewData
import com.example.calcnumeric.presenter.model.HistoryUiModel
import com.example.calcnumeric.presenter.utils.DateFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryListUseCase: GetHistoryAllUseCase,
    private val deleteHistoryByIdUseCase: DeleteHistoryByIdUseCase,
    private val clearHistoryUseCase: ClearHistoryUseCase,
    private val restoreHistoryUseCase: RestoreHistoryUseCase,
    private val dispatcher: DispatcherProvider
) : BaseViewModel<ViewData>(ViewData()) {

    init {
        log.d("init")
        reload()
    }

    fun reload() {
        log.d("reload")
        fetch()
    }

    private fun fetch() {
        safeRunJob(dispatcher.default()) {
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
        when (clearHistoryUseCase()) {
            is Results.Success -> {
                log.d("clear success")
                fetch()
            }

            is Results.Failure -> {
                log.d("clear failed")
            }

            is Results.Loading -> {
                log.d("clear loading")
            }
        }
    }


    fun deleteHistoryById(id: Int) = safeRunJob(dispatcher.default()) {
        log.d("call with id: $id")
        when (deleteHistoryByIdUseCase(id)) {
            is Results.Success -> {
                log.d("delete success")
                fetch()
            }

            is Results.Failure -> {
                log.d("delete failed")
            }

            is Results.Loading -> {
                log.d("delete loading")
            }
        }
    }

    fun restoreHistory(history: History) = safeRunJob(dispatcher.default()) {
        log.d("call with history: $history")
        when (restoreHistoryUseCase(history)) {
            is Results.Success -> {
                log.d("restore success")
                fetch()
            }

            is Results.Failure -> {
                log.d("restore failed")
            }

            is Results.Loading -> {
                log.d("restore loading")
            }
        }
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