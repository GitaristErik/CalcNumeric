package com.example.calcnumeric.data.repository

import com.example.calcnumeric.domain.entity.Results
import com.example.calcnumeric.domain.repository.CalculatorRepository
import com.github.ayaanqui.expressionresolver.Resolver

class CalculatorRepositoryImpl : CalculatorRepository {

    private var calculator = Resolver()

    override fun calculate(expression: String): Results<Number> {
        if (expression.isEmpty()) {
            calculator = Resolver()
            return Results.Loading(0)
        }
        val res = calculator.setExpression(expression).solveExpression()
        return if (res.success) {
            Results.Success(res.result)
        } else {
            val lastResult = try {
                calculator.lastResult
            } catch (e: Exception) {
                0
            }
            Results.Failure(
                Exception(res.errors.joinToString("\n")),
                Results.FailureType.CLIENT,
                lastResult
            )
        }
    }
}