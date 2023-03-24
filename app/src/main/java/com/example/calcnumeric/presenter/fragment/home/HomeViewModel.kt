package com.example.calcnumeric.presenter.fragment.home

import com.example.calcnumeric.domain.entity.History
import com.example.calcnumeric.domain.entity.Results
import com.example.calcnumeric.domain.helper.DispatcherProvider
import com.example.calcnumeric.domain.usecase.CalculateExpressionUseCase
import com.example.calcnumeric.domain.usecase.InsertHistoryUseCase
import com.example.calcnumeric.presenter.BaseViewModel
import com.example.calcnumeric.presenter.fragment.home.HomeViewModel.ViewData
import com.example.calcnumeric.presenter.utils.NumberFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val calculateExpressionUseCase: CalculateExpressionUseCase,
    private val insertHistoryUseCase: InsertHistoryUseCase
) : BaseViewModel<ViewData>(ViewData()) {

    fun calculate(expression: String, save: Boolean = false) = safeRunJob(dispatcher.default()) {
        calculateExpressionUseCase(expression).let { result ->
            _data.update {
                ViewData(expression = expression, result = result)
            }
            if (save && result is Results.Success) {
                val history = History(
                    expression = _data.value.expression,
                    result = NumberFormatter.format(result.data),
                    Date().time
                )
                insertHistoryUseCase(history)
            }
        }
    }

    data class ViewData(
        val expression: String = "",
        val result: Results<Number> = Results.Loading(0)
    )
}