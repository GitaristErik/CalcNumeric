package com.example.calcnumeric.domain.repository

import com.example.calcnumeric.domain.entity.History
import com.example.calcnumeric.domain.entity.Results
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    suspend fun getAll(): Flow<Results<List<History>>>

    suspend fun insert(history: History): Results<Unit>

    suspend fun deleteAll(): Results<Unit>

    suspend fun deleteByExpression(expression: String): Results<Unit>

}