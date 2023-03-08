package com.example.calcnumeric.presenter.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calcnumeric.di.DispatcherProvider
import com.example.calcnumeric.presenter.BaseViewModel
import com.example.calcnumeric.presenter.fragment.home.HomeViewModel.ViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider
) : BaseViewModel<ViewData>(ViewData()) {

    init {
        Timber.d("init")
    }

    class ViewData {}
}