package com.example.calcnumeric.data.repository

import com.example.calcnumeric.data.datasource.CalculatorDatabase
import com.example.calcnumeric.data.datasource.HistoryDao
import com.example.calcnumeric.data.model.HistoryEntity
import com.example.calcnumeric.data.model.toDomain
import com.example.calcnumeric.data.model.toEntity
import com.example.calcnumeric.domain.entity.History
import com.example.calcnumeric.domain.entity.Results
import com.example.calcnumeric.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class HistoryRepositoryImpl /*@Inject constructor*/(
    database: CalculatorDatabase
) : HistoryRepository {

    private val dao: HistoryDao = database.historyDao()

    override suspend fun getAll(): Flow<Results<List<History>>> {
        return try {
            dao.getAll().map {
                Results.Success(
                    it.map(HistoryEntity::toDomain)
                )
            }
        } catch (e: Exception) {
            flow {
                emit(Results.Failure<List<History>>(e, Results.FailureType.SERVER))
            }
        }
    }

    override suspend fun insert(history: History): Results<Unit> {
        return try {
            dao.insert(history.toEntity())
            Results.Success(Unit)
        } catch (e: Exception) {
            Results.Failure(e, Results.FailureType.SERVER)
        }
    }

    override suspend fun deleteAll(): Results<Unit> {
        return try {
            dao.deleteAll()
            Results.Success(Unit)
        } catch (e: Exception) {
            Results.Failure(e, Results.FailureType.SERVER)
        }
    }

    override suspend fun deleteByExpression(expression: String): Results<Unit> {
        return try {
            dao.deleteByExpression(expression)
            Results.Success(Unit)
        } catch (e: Exception) {
            Results.Failure(e, Results.FailureType.SERVER)
        }
    }
}