package com.example.calcnumeric.presenter.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
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
        log.d("initializeView")
    }

    override fun render(data: ViewData) {
        log.d("render")
    }
}