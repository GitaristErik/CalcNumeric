package com.example.calcnumeric.domain.usecase

import com.example.calcnumeric.domain.model.History
import com.example.calcnumeric.domain.model.Results
import com.example.calcnumeric.domain.repository.HistoryRepository
import javax.inject.Inject

open class RestoreHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    open suspend operator fun invoke(history: History): Results<Unit> =
        historyRepository.restore(history)
}