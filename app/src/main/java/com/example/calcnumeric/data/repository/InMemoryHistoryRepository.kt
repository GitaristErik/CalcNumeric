package com.example.calcnumeric.data.repository

import com.example.calcnumeric.domain.entity.History
import com.example.calcnumeric.domain.entity.Results
import com.example.calcnumeric.domain.repository.HistoryRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import java.util.Date
import java.util.TreeSet
import kotlin.math.pow

class InMemoryHistoryRepository : HistoryRepository {

    private val historyList: TreeSet<History> = sortedSetOf(
        compareBy(History::date)
    )

    private val _historyChanel = Channel<Results<List<History>>>()
    private val historyChanel: ReceiveChannel<Results<List<History>>>
        get() = _historyChanel

    init {
        historyList.addAll(loadData())
        _historyChanel.trySend(Results.Success(historyList.toList()))
    }

    override suspend fun getAll(): Flow<Results<List<History>>> {
        return historyChanel.receiveAsFlow()
    }

    override suspend fun insert(history: History): Results<Unit> {
        return try {
            historyList.add(history)
            _historyChanel.trySend(Results.Success(historyList.toList()))
            Results.Success(Unit)
        } catch (e: Exception) {
            Results.Failure(e, Results.FailureType.CLIENT)
        }
    }

    override suspend fun deleteAll(): Results<Unit> {
        return try {
            historyList.clear()
            _historyChanel.trySend(Results.Success(emptyList()))
            Results.Success(Unit)
        } catch (e: Exception) {
            Results.Failure(e, Results.FailureType.CLIENT)
        }
    }

    override suspend fun deleteByExpression(expression: String): Results<Unit> {
        return try {
            historyList.remove(historyList.first { it.expression == expression })
            _historyChanel.trySend(Results.Success(historyList.toList()))
            Results.Success(Unit)
        } catch (e: NoSuchElementException) {
            Results.Failure(e, Results.FailureType.CLIENT)
        }
    }

    private fun loadData(): List<History> {
        val DAY_IN_MILISECONDS = 24 * 60 * 60 * 1000
        val currentDate = Date().time

        return List(25) { index ->
            // generate a new date for each item
            val date = Date(currentDate - (index / 5) * DAY_IN_MILISECONDS).time
            // generate a new calculation for each item
            val expression = "${index * 5 + index.toDouble().pow(3).toInt()}+${index * 3}"
            // generate a new answer for each item
            val answer = (index * 5 + index.toDouble().pow(3).toInt() + index * 3).toString()
            // return a new History object for each item
            History(expression, answer, date)
        }
    }
}