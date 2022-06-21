package com.example.calculator

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    var state by mutableStateOf(CalculatorState())

    fun onAction(action: CalculatorActions) {
        Log.d(TAG,"The onAction method is called")
        when (action) {
            is CalculatorActions.numbers -> enterNumber(action.num)
            is CalculatorActions.calculate -> calculateAnswer()
            is CalculatorActions.clear -> state = CalculatorState()
            is CalculatorActions.operation -> enterOperation(action.op)
            is CalculatorActions.decimal -> enterDecimal()
            is CalculatorActions.delete -> performDeletion()
        }
    }

    private fun enterDecimal() {
        if (state.operation_state == null && !state.number1.contains(".") && state.number1.isNotBlank()) {
            state = state.copy(number1 = state.number1 + ".")
            return
        }
        if (!state.number2.contains(".") && state.number2.isNotBlank()) {
            state = state.copy(number2 = state.number2 + ".")
        }
    }

    private fun performDeletion() {
        when {
            state.number2.isNotBlank() -> state = state.copy(number2 = state.number2.dropLast(1))
            state.operation_state != null -> state = state.copy(operation_state = null)
            state.number1.isNotBlank() -> state = state.copy(number1 = state.number1.dropLast(1))
        }
    }

    private fun calculateAnswer() {
        val number1 = state.number1.toDoubleOrNull()
        val number2 = state.number2.toDoubleOrNull()
        if (number1 != null && number2 != null) {
            val result = when (state.operation_state) {
                is CalculatorOperation.Add -> number1 + number2
                is CalculatorOperation.Subtract -> number1 - number2
                is CalculatorOperation.Multiply -> number1 * number2
                is CalculatorOperation.Divide -> number1 / number2
                null -> return
            }
            state = state.copy(
                number1 = result.toString().take(15),
                number2 = "",
                operation_state = null
            )
        }
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if (state.number1.isNotBlank()) {
            state = state.copy(operation_state = operation)
            Log.d(TAG, "The operation has been entered")
        }
    }

    fun enterNumber(number: Int) {
        if (state.operation_state == null) {
            if (state.number1.length >= MAX_LENGTH) {
                return
            }
            state = state.copy(number1 = state.number1 + number)
        } else {
            if (state.number2.length >= MAX_LENGTH) {
                return
            }
            state = state.copy(number2 = state.number2 + number)
        }
    }

    companion object {
        private const val MAX_LENGTH = 9
    }
}