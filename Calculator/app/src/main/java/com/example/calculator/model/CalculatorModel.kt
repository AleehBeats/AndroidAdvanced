package com.example.calculator.model

import com.example.calculator.contract.Contract.Model

class CalculatorModel : Model {
    var firstNumber: String = ""
    var secondNumber: String = ""
    var operationType: OperationType = OperationType.CLEAR
    var state: State = State.EMPTY
    override fun plus(): String {
        return (firstNumber.toDouble() + secondNumber.toDouble()).toString()
    }

    override fun minus(): String {
        return (firstNumber.toDouble() - secondNumber.toDouble()).toString()
    }

    override fun multiply(): String {
        return (firstNumber.toDouble() * secondNumber.toDouble()).toString()
    }

    override fun divide(): String {
        val result = firstNumber.toDouble() / secondNumber.toDouble()
        val resultString: String
        resultString =
            if (result != Double.POSITIVE_INFINITY || result != Double.NEGATIVE_INFINITY) {
                result.toString()
            } else {
                "You can't divide to zero"
            }
        return resultString
    }


}