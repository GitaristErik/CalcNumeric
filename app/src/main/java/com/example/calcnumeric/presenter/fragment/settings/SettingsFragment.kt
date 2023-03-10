package com.example.calcnumeric.presenter.fragment.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.calcnumeric.databinding.FragmentSettingsBinding
import com.example.calcnumeric.presenter.fragment.BaseViewModelFragment
import com.example.calcnumeric.presenter.fragment.settings.SettingsViewModel.ViewData
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment :
    BaseViewModelFragment<FragmentSettingsBinding, ViewData, SettingsViewModel>() {

    override val viewModel: SettingsViewModel by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSettingsBinding
        get() = FragmentSettingsBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initializeView() {
        log.d("initializeView")
    }

    override fun render(data: ViewData) {
        log.d("render")
    }
}