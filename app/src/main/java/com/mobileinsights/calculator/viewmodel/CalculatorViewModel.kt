package com.mobileinsights.calculator.viewmodel

import androidx.lifecycle.ViewModel
import com.mobileinsights.calculator.model.Calculator
import com.mobileinsights.calculator.model.Operation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CalculatorViewModel : ViewModel() {
    private val _mutableEraserState = MutableStateFlow(false)
    private val _mutableMemoryState =  MutableStateFlow<Float?>(null)
    private val _mutableButtonState = MutableStateFlow(Operation.NONE)
    private val _mutableEntryState = MutableStateFlow("0")

    val buttonState = _mutableButtonState.asStateFlow()
    val entryState= _mutableEntryState.asStateFlow()

    fun onEvent(calculatorEvent: CalculatorEvent) {
        when (calculatorEvent) {
            CalculatorEvent.AllClear -> allClear()
            CalculatorEvent.Equals -> equals()
            is CalculatorEvent.Calculation -> handleOperation(calculatorEvent.operation)
            is CalculatorEvent.Number -> enterNumber(calculatorEvent.value)
        }
    }

    private fun handleOperation(operation: Operation) {
        when (operation) {
            Operation.NONE -> Unit
            Operation.AC -> allClear()
            Operation.PLUS_MINUS -> toggleSign()
            Operation.COMMA -> insertDecimalPoint()
            Operation.PERCENTAGE -> percentage()
            Operation.EQUALS -> equals()
            else -> selectOperator(operation)
        }
    }

    private fun enterNumber(entry: Long) {
        if (_mutableEntryState.value.length >= 12) {
            return
        }
        if (_mutableEraserState.value) {
            _mutableEntryState.value = "0"
            _mutableEraserState.value = false
        }
        val valueBuilder = StringBuilder()
        if (_mutableEntryState.value != "0") {
            valueBuilder.append(_mutableEntryState.value)
        }
        valueBuilder.append(entry)
        _mutableEntryState.value = valueBuilder.toString()
    }

    private fun insertDecimalPoint() {
        if (_mutableEntryState.value.contains(".")) {
            return
        }
        if (_mutableEntryState.value.length >= 12) {
            return
        }
        if (_mutableEraserState.value) {
            _mutableEntryState.value = "0"
            _mutableEraserState.value = false
        }
        _mutableEntryState.value = _mutableEntryState.value + "."
    }

    private fun toggleSign() {
        val current = _mutableEntryState.value
        if (current == "0") {
            return
        }
        _mutableEntryState.value = if (current.startsWith("-")) {
            current.removePrefix("-")
        } else {
            "-$current"
        }
    }

    private fun allClear() {
        _mutableEntryState.value = "0"
        _mutableMemoryState.value = null
        _mutableButtonState.value = Operation.NONE
        _mutableEraserState.value = false
    }

    private fun percentage() {
        val currentValue = _mutableEntryState.value.toFloatOrNull() ?: return
        _mutableEntryState.value = (currentValue / 100f).toString()
    }

    private fun equals() {
        if (_mutableButtonState.value == Operation.NONE) {
            _mutableMemoryState.value = _mutableEntryState.value.toFloat()
            _mutableEraserState.value = true
            return
        }
        val total = calculation(
            _mutableMemoryState.value ?: 0f,
            _mutableEntryState.value.toFloat(),
            _mutableButtonState.value
        )
        if (total is Float) {
            _mutableMemoryState.value = total
            _mutableEntryState.value = total.toString()
        }
        _mutableEraserState.value = true
        _mutableButtonState.value = Operation.NONE
    }

    private fun selectOperator(
        button: Operation
    ) {
        if (_mutableEraserState.value.not())  {
            if (_mutableMemoryState.value == null) {
                _mutableMemoryState.value = _mutableEntryState.value.toFloat()
            } else {
                val total = calculation(
                    actual = _mutableMemoryState.value ?: 0f,
                    entry = _mutableEntryState.value.toFloat(),
                    _mutableButtonState.value
                )
                if (total is Float) {
                    _mutableMemoryState.value = total
                }
                _mutableEntryState.value = total.toString()
            }
            _mutableButtonState.value = button
            _mutableEraserState.value = true
        } else  {
            _mutableButtonState.value = button
        }
    }

    private fun calculation(
        actual: Float,
        entry: Float,
        currentOperation: Operation
    ): Any {
        return when (currentOperation) {
            Operation.DIVISION -> Calculator.Division(actual, entry)()
            Operation.MULTIPLICATION -> Calculator.Multiplication(actual, entry)()
            Operation.SUBTRACTION -> Calculator.Subtraction(actual, entry)()
            Operation.ADDITION -> Calculator.Addition(actual, entry)()
            Operation.PERCENTAGE -> entry / 100f
            else -> entry
        }
    }
}

sealed class CalculatorEvent{
    data class Number(val value: Long) : CalculatorEvent()
    data class Calculation(val operation: Operation): CalculatorEvent()
    object Equals : CalculatorEvent()
    object AllClear : CalculatorEvent()
}