package com.example.calcnumeric.domain.repository

import com.example.calcnumeric.domain.entity.History
import com.example.calcnumeric.domain.entity.Results
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    fun getAll(): Flow<Results<List<History>>>

    fun getById(id: Int): Results<History>

    fun add(expression: String, result: Number): Results<Unit>

    fun deleteAll(): Results<Unit>

    fun deleteById(id: Int): Results<Unit>

    fun restore(history: History): Results<Unit>
}