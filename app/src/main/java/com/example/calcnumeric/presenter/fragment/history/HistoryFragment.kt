package com.example.calcnumeric.presenter.fragment.history

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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calcnumeric.R
import com.example.calcnumeric.databinding.FragmentHistoryBinding
import com.example.calcnumeric.domain.entity.History
import com.example.calcnumeric.domain.entity.Results
import com.example.calcnumeric.presenter.fragment.BaseViewModelFragment
import com.example.calcnumeric.presenter.fragment.history.HistoryViewModel.ViewData
import com.example.calcnumeric.presenter.fragment.history.adapter.HistoryAdapter
import com.example.calcnumeric.presenter.fragment.history.adapter.HistoryDividerItemDecoration
import com.example.calcnumeric.presenter.fragment.history.adapter.HistoryItemSwipeCallback
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment :
    BaseViewModelFragment<FragmentHistoryBinding, ViewData, HistoryViewModel>() {

    override val viewModel: HistoryViewModel by viewModels()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHistoryBinding
        get() = FragmentHistoryBinding::inflate

    private val historyAdapter by lazy {
        HistoryAdapter(
            itemClickListener = {
                log.d("clicked: $it")
                viewModel.setClickedExpression(it.expression)
            },
            itemRemovedListener = {
                viewModel.deleteHistoryByExpression(it.expression)
                showRestoreSnackbar(it)
            }
        )
    }

    override fun initializeView() {
        setupActionBar()
        setupAdapter()
    }

    private fun setupAdapter(): Unit = binding.rvHistory.run {
        layoutManager = LinearLayoutManager(context).apply {
            stackFromEnd = true
        }

        adapter = historyAdapter

        val itemDecorator = HistoryDividerItemDecoration(
            context,
            LinearLayoutManager.VERTICAL
        ).apply {
            val pad = resources.getDimensionPixelOffset(R.dimen.margin_large_extra_double)
            dividerInsetEnd = pad
            dividerInsetStart = pad
        }
        addItemDecoration(itemDecorator)

        val swipeCallback = HistoryItemSwipeCallback(
            context,
            historyAdapter,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        )
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(this)
    }

    override fun render(data: ViewData) {
        when (data.uiData) {
            is Results.Success -> {
                data.uiData.data.let {
                    binding.noHistory.isVisible = it.isEmpty()
                    historyAdapter.submitList(it)
                }
            }

            is Results.Loading -> log.d("loading: ${data.uiData}")
            is Results.Failure -> log.d("error: ${data.uiData}")
        }
    }

    private fun showRestoreSnackbar(history: History) {
        Snackbar.make(
            binding.root,
            resources.getString(R.string.history_item_swipe_undo_title),
            Snackbar.LENGTH_LONG
        ).setAction(resources.getString(R.string.history_item_swipe_undo_button)) {
            viewModel.restoreHistory(history)
        }.show()
    }

    private fun showDeleteDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(resources.getString(R.string.history_modal_message))
            .setTitle(resources.getString(R.string.history_modal_title))
            .setIcon(R.drawable.ic_trash_24dp)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                viewModel.clearHistory()
            }
            .setNegativeButton(R.string.history_modal_cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
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
                    showDeleteDialog()
                    true
                }

                else -> false
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.history_menu, menu)
            }
        }, viewLifecycleOwner, Lifecycle.State.CREATED)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = item.groupId
        val history = historyAdapter.getHistoryFromId(position)
        historyAdapter.onRemoved(history)
        return true
    }
}