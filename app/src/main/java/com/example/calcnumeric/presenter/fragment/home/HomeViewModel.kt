package com.example.calcnumeric.presenter.fragment.home

import com.example.calcnumeric.domain.DispatcherProvider
import com.example.calcnumeric.presenter.BaseViewModel
import com.example.calcnumeric.presenter.fragment.home.HomeViewModel.ViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider
) : BaseViewModel<ViewData>(ViewData()) {

    init {
        log.d(" ")
    }

    class ViewData {}
}