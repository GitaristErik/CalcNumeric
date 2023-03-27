package com.example.calcnumeric.domain.usecase

import com.example.calcnumeric.domain.entity.Results
import com.example.calcnumeric.domain.repository.HistoryRepository

open class ClearHistoryUseCase(
    private val historyRepository: HistoryRepository
) {
    open suspend operator fun invoke(): Results<Unit> = historyRepository.deleteAll()
}