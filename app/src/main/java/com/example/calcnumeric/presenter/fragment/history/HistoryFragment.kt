package com.example.calcnumeric.presenter.fragment.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.example.calcnumeric.R
import com.example.calcnumeric.databinding.FragmentHistoryBinding
import com.example.calcnumeric.presenter.fragment.BaseViewModelFragment
import com.example.calcnumeric.presenter.fragment.history.HistoryViewModel.ViewData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment :
    BaseViewModelFragment<FragmentHistoryBinding, ViewData, HistoryViewModel>() {

    override val viewModel: HistoryViewModel by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHistoryBinding
        get() = FragmentHistoryBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initializeView() {
        log.d(" ")
        setupActionBar()
    }

    override fun render(data: ViewData) {
        log.d(" ")
    }

    private fun setupActionBar() {
        with(requireActivity() as AppCompatActivity) {
            setSupportActionBar(binding.toolbar)
        }
        setupMenu()
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onMenuItemSelected(menuItem: MenuItem) = when (menuItem.itemId) {
                R.id.clear_history -> {
                    viewModel.clearHistory()
                    true
                }

                else -> false
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.history_menu, menu)
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}