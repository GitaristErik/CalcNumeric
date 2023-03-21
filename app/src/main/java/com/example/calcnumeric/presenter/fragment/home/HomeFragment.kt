package com.example.calcnumeric.presenter.fragment.home

import android.animation.LayoutTransition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.calcnumeric.R
import com.example.calcnumeric.databinding.FragmentHomeBinding
import com.example.calcnumeric.presenter.fragment.BaseViewModelFragment
import com.example.calcnumeric.presenter.fragment.home.HomeViewModel.ViewData
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseViewModelFragment<FragmentHomeBinding, ViewData, HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun initializeView() {
        initSwitchMenu()
    }

    override fun render(data: ViewData) {
        log.d(" ")
    }

    private fun initSwitchMenu() {
        binding.tableLayout.layoutTransition = LayoutTransition().apply {
             disableTransitionType(LayoutTransition.DISAPPEARING)
        }

        binding.btnSwitchAdditional.setOnClickListener {
            if (it is MaterialButton) {
                if (binding.menuAdditional.isVisible) {
                    binding.menuAdditional.visibility = View.GONE
                    it.setIconResource(R.drawable.ic_arrow_up_24dp)
                } else {
                    binding.menuAdditional.visibility = View.VISIBLE
                    it.setIconResource(R.drawable.ic_arrow_down_24dp)
                }
            }
        }
    }
}