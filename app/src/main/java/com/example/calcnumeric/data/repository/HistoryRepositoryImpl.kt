package com.example.calcnumeric.data.repository

import com.example.calcnumeric.domain.model.History
import com.example.calcnumeric.domain.model.Results
import com.example.calcnumeric.domain.repository.HistoryRepository
import timber.log.Timber
import java.util.Date
import javax.inject.Inject
import kotlin.math.pow

class HistoryRepositoryImpl @Inject constructor() : HistoryRepository {

    private val historyList: MutableList<History> = mutableListOf()

    init {
        historyList.addAll(loadData())
    }

    override fun getAll(): Results<List<History>> {
        return Results.Success(historyList)
    }

    override fun getById(id: Int): Results<History> {
        return Results.Success(historyList.first { it.id == id })
    }

    override fun add(history: History): Results<Unit> {
        historyList.add(history)
        return Results.Success(Unit)
    }

    override fun deleteAll(): Results<Unit> {
        historyList.clear()
        return Results.Success(Unit)
    }

    override fun deleteById(id: Int): Results<Unit> {
        historyList.remove(historyList.first { it.id == id })
        return Results.Success(Unit)
    }

    private fun loadData(): List<History> {
        val DAY_IN_MILISECONDS = 24 * 60 * 60 * 1000
        val currentDate = Date().time

        val historyList = List(25) { index ->
            // generate a new date for each item
            val date = Date(currentDate - (index / 5) * DAY_IN_MILISECONDS).time
            Timber.d("index: $index date: $date index % 5: ${index % 5}")
            // generate a new calculation for each item
            val expression = "${index * 5 + index.toDouble().pow(3).toInt()}+${index * 3}"
            // generate a new answer for each item
            val answer = (index * 5 + index.toDouble().pow(3).toInt() + index * 3).toString()
            // return a new History object for each item
            History(index, date, expression, answer)
        }

        return historyList.asReversed()
    }
}