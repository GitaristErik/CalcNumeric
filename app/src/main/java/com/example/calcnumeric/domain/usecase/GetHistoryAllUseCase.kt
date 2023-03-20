package com.example.calcnumeric.domain.usecase

import com.example.calcnumeric.domain.DispatcherProvider
import com.example.calcnumeric.domain.model.History
import com.example.calcnumeric.domain.model.Results
import com.example.calcnumeric.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

open class GetHistoryAllUseCase @Inject constructor(
    private val historyRepository: HistoryRepository,
    private val dispatcher: DispatcherProvider
) {
    open suspend operator fun invoke(): Flow<Results<List<History>>> = flow {
        emit(historyRepository.getAll())
    }.flowOn(dispatcher.io())
}