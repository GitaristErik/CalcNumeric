package com.example.calcnumeric.presenter.fragment.settings

import com.example.calcnumeric.domain.helper.DispatcherProvider
import com.example.calcnumeric.presenter.BaseViewModel
import com.example.calcnumeric.presenter.fragment.settings.SettingsViewModel.ViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider
) : BaseViewModel<ViewData>(ViewData()) {

    init {
        log.d("init")
    }

    class ViewData
}