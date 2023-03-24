package com.example.calcnumeric.domain.usecase

import com.example.calcnumeric.domain.entity.History
import com.example.calcnumeric.domain.entity.Results
import com.example.calcnumeric.domain.helper.DispatcherProvider
import com.example.calcnumeric.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

open class GetHistoryAllUseCase @Inject constructor(
    private val historyRepository: HistoryRepository,
    private val dispatcher: DispatcherProvider
) {
    open suspend operator fun invoke(): Flow<Results<List<History>>> =
        historyRepository.getAll()
        .flowOn(dispatcher.io())
}