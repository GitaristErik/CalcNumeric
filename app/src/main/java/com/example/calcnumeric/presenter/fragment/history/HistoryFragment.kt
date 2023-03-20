package com.example.calcnumeric.presenter.fragment.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calcnumeric.R
import com.example.calcnumeric.databinding.FragmentHistoryBinding
import com.example.calcnumeric.domain.model.Results
import com.example.calcnumeric.presenter.fragment.BaseViewModelFragment
import com.example.calcnumeric.presenter.fragment.history.HistoryViewModel.ViewData
import com.example.calcnumeric.presenter.fragment.history.adapter.HistoryAdapter
import com.example.calcnumeric.presenter.fragment.history.adapter.HistoryDividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment :
    BaseViewModelFragment<FragmentHistoryBinding, ViewData, HistoryViewModel>() {

    override val viewModel: HistoryViewModel by viewModels()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHistoryBinding
        get() = FragmentHistoryBinding::inflate

    private val historyAdapter by lazy {
        HistoryAdapter {
            log.d("clicked: $it")
            viewModel.setClickedExpression(it.expression)
        }
    }

    override fun initializeView() {
        setupActionBar()
        setupAdapter()
    }

    @SuppressLint("ResourceType")
    private fun setupAdapter() {
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(context).apply {
                stackFromEnd = true
            }
            adapter = historyAdapter
            val itemDecorator =
                HistoryDividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply {
                    val pad = resources.getDimensionPixelOffset(R.dimen.margin_large_extra_double)
                    dividerInsetEnd = pad
                    dividerInsetStart = pad
                }
            addItemDecoration(itemDecorator)
        }
    }

    override fun render(data: ViewData) {
        when (data.uiData) {
            is Results.Success -> {
                data.uiData.data.let {
                    binding.noHistory.isVisible = it.isEmpty()
                    historyAdapter.submitList(it)
                }
            }

            is Results.Loading -> {
                log.d("loading: ${data.uiData}")
            }

            is Results.Failure -> {
                log.d("error: ${data.uiData}")
            }
        }
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

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = item.groupId
        val history = historyAdapter.getHistoryFromGroupId(position)
        viewModel.deleteHistoryById(history.id)
        return true
    }
}