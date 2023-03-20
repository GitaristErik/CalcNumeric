package com.example.calcnumeric.domain.usecase

import com.example.calcnumeric.domain.model.Results
import com.example.calcnumeric.domain.repository.HistoryRepository
import javax.inject.Inject

open class DeleteHistoryByIdUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    open suspend operator fun invoke(id: Int): Results<Unit> = historyRepository.deleteById(id)
}