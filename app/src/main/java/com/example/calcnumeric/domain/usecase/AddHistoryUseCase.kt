package com.example.calcnumeric.domain.usecase

import com.example.calcnumeric.domain.entity.Results
import com.example.calcnumeric.domain.repository.HistoryRepository
import javax.inject.Inject

open class AddHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    open suspend operator fun invoke(expression: String, result: Number): Results<Unit> =
        historyRepository.add(expression, result)
}