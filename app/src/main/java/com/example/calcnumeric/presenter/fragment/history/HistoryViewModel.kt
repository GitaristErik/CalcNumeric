package com.example.calcnumeric.presenter.fragment.history

import com.example.calcnumeric.di.DispatcherProvider
import com.example.calcnumeric.presenter.BaseViewModel
import com.example.calcnumeric.presenter.fragment.history.HistoryViewModel.ViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider
) : BaseViewModel<ViewData>(ViewData()) {

    init {
        log.d("init")
    }

    fun clearHistory() {
        log.d("call")
    }

    class ViewData
}