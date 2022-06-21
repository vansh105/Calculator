package com.example.calculator

sealed class CalculatorActions{
    data class numbers(var num:Int):CalculatorActions()
    object clear:CalculatorActions()
    object delete:CalculatorActions()
    object decimal:CalculatorActions()
    object calculate:CalculatorActions()
    data class operation(val op:CalculatorOperation):CalculatorActions()
}
