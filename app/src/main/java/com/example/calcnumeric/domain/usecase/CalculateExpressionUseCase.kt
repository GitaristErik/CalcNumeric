package com.example.calcnumeric.domain.usecase

import com.example.calcnumeric.domain.entity.Results
import com.example.calcnumeric.domain.repository.CalculatorRepository
import javax.inject.Inject

open class CalculateExpressionUseCase @Inject constructor(
    private val repository: CalculatorRepository
) {
    open operator fun invoke(expression: String): Results<Number> = repository.calculate(expression)
}