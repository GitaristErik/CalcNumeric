package com.example.calcnumeric.presenter.fragment.history.adapter

import com.example.calcnumeric.domain.model.History

fun interface HistoryItemClickListener {
    fun onItemClicked(history: History)
}