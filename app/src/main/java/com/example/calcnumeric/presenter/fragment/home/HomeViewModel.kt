package com.example.calcnumeric.presenter.fragment.home

import com.example.calcnumeric.domain.entity.Results
import com.example.calcnumeric.domain.helper.DispatcherProvider
import com.example.calcnumeric.domain.usecase.AddHistoryUseCase
import com.example.calcnumeric.domain.usecase.CalculateExpressionUseCase
import com.example.calcnumeric.presenter.BaseViewModel
import com.example.calcnumeric.presenter.fragment.home.HomeViewModel.ViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val calculateExpressionUseCase: CalculateExpressionUseCase,
    private val addHistoryUseCase: AddHistoryUseCase
) : BaseViewModel<ViewData>(ViewData()) {

    fun calculate(expression: String, save: Boolean = false) =
        safeRunJob(dispatcher.default()) {
            calculateExpressionUseCase(expression).let { result ->
                _data.update {
                    ViewData(
                        expression = expression,
                        result = result
                    )
                }
                if (save && result is Results.Success) {
                    addHistoryUseCase(
                        expression = _data.value.expression,
                        result = result.data
                    )
                }
            }
        }


    init {
        log.d(" ")
    }

    data class ViewData(
        val expression: String = "",
        val result: Results<Number> = Results.Loading(0)
    )
}