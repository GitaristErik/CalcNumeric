package com.example.calcnumeric.presenter.utils

object NumberFormatter {

    fun format(number: Number): String {
        return String.format(
            "%.10f",
            number.toDouble()
        ).dropLastWhile { it in arrayOf('0', '.') }
    }
}