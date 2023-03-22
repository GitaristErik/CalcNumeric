package com.example.calcnumeric.presenter.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.calcnumeric.presenter.BaseViewModel

abstract class BaseViewModelFragment<VB : ViewBinding, VD, VM : BaseViewModel<VD>> :
    BaseFragment<VB>() {

    protected abstract val viewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        log.d("base with ViewModel")
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.data
//                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { render(it) }
        }
    }

    protected open fun render(data: VD) = Unit
}