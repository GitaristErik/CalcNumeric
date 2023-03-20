package com.example.calcnumeric.domain.usecase

import com.example.calcnumeric.domain.model.Results
import com.example.calcnumeric.domain.repository.HistoryRepository
import javax.inject.Inject

open class ClearHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    open suspend operator fun invoke(): Results<Unit> = historyRepository.deleteAll()
}