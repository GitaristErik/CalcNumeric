package com.example.calcnumeric.presenter.fragment.history.adapter

import com.example.calcnumeric.domain.entity.History

fun interface HistoryItemClickListener {
    fun onItemClicked(history: History)
}