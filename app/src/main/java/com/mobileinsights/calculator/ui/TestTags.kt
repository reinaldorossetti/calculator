package com.mobileinsights.calculator.ui

import com.mobileinsights.calculator.model.Operation

object TestTags {
    const val DISPLAY = "calculator_display"

    fun number(value: String) = "btn_number_$value"

    fun operator(operation: Operation) = "btn_operator_${operation.name.lowercase()}"

    fun special(operation: Operation) = "btn_special_${operation.name.lowercase()}"
}
