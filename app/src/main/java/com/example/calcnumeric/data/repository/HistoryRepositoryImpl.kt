package com.example.calcnumeric.data.repository

import com.example.calcnumeric.domain.entity.History
import com.example.calcnumeric.domain.entity.Results
import com.example.calcnumeric.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import java.util.TreeSet
import javax.inject.Inject
import kotlin.math.pow

class HistoryRepositoryImpl @Inject constructor() : HistoryRepository {

    private val historyList: TreeSet<History> = sortedSetOf(
        compareBy(History::date)
            .thenByDescending(History::id)
    )

    init {
//        historyList.addAll(loadData())
    }

    override fun getAll(): Flow<Results<List<History>>> = flow {
        emit(Results.Success(historyList.toList()))
    }

    override fun getById(id: Int): Results<History> {
        return try {
            Results.Success(historyList.first { it.id == id })
        } catch (e: NoSuchElementException) {
            Results.Failure(e, Results.FailureType.CLIENT)
        }
    }

    override fun add(expression: String, result: Number): Results<Unit> {
        return try {
            val history = History(
                id = historyList.size + 1,
                date = Date().time,
                expression = expression,
                result = result.toString()
            )
            historyList.add(history)
            Results.Success(Unit)
        } catch (e: Exception) {
            Results.Failure(e, Results.FailureType.CLIENT)
        }
    }

    override fun deleteAll(): Results<Unit> {
        return try {
            historyList.clear()
            Results.Success(Unit)
        } catch (e: Exception) {
            Results.Failure(e, Results.FailureType.CLIENT)
        }
    }

    override fun deleteById(id: Int): Results<Unit> {
        return try {
            historyList.remove(historyList.first { it.id == id })
            Results.Success(Unit)
        } catch (e: NoSuchElementException) {
            Results.Failure(e, Results.FailureType.CLIENT)
        }
    }

    override fun restore(history: History): Results<Unit> {
        return try {
            historyList.add(history)
            Results.Success(Unit)
        } catch (e: Exception) {
            Results.Failure(e, Results.FailureType.CLIENT)
        }
    }

    private fun loadData(): List<History> {
        val DAY_IN_MILISECONDS = 24 * 60 * 60 * 1000
        val currentDate = Date().time

        val historyList = List(25) { index ->
            // generate a new date for each item
            val date = Date(currentDate - (index / 5) * DAY_IN_MILISECONDS).time
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