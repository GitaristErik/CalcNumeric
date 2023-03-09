package com.example.calcnumeric.presenter.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.calcnumeric.databinding.FragmentHomeBinding
import com.example.calcnumeric.presenter.fragment.BaseViewModelFragment
import com.example.calcnumeric.presenter.fragment.home.HomeViewModel.ViewData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseViewModelFragment<FragmentHomeBinding, ViewData, HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initializeView() {
        log.d(" ")
    }

    override fun render(data: ViewData) {
        log.d(" ")
    }
}