package com.example.calcnumeric.domain.repository

import com.example.calcnumeric.domain.entity.Results

interface CalculatorRepository {
    fun calculate(expression: String): Results<Number>
}