package com.example.calculator

sealed class CalculatorOperation(var symbol:String){
    object Add:CalculatorOperation("+")
    object Subtract:CalculatorOperation("-")
    object Multiply:CalculatorOperation("x")
    object Divide: CalculatorOperation("/")
}
