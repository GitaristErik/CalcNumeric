package com.example.calcnumeric.domain.model

data class History(
    val id: Int,
    val date: Long,
    val expression: String,
    val result: String,
)