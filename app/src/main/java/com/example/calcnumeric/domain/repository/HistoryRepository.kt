package com.example.calcnumeric.domain.repository

import com.example.calcnumeric.domain.model.History
import com.example.calcnumeric.domain.model.Results
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getAll(): Flow<Results<List<History>>>

    fun getById(id: Int): Results<History>

    fun add(history: History): Results<Unit>

    fun deleteAll(): Results<Unit>

    fun deleteById(id: Int): Results<Unit>

    fun restore(history: History): Results<Unit>
}