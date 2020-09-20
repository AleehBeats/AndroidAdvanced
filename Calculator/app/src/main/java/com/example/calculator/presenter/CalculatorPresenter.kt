package com.example.calculator.presenter


import com.example.calculator.contract.Contract
import com.example.calculator.contract.Contract.Presenter
import com.example.calculator.model.CalculatorModel
import com.example.calculator.model.OperationType
import com.example.calculator.model.State
import com.example.calculator.utils.cutString
import com.example.calculator.utils.toEnum

class CalculatorPresenter(private val view: Contract.View): Presenter {
    private var calculatorModel = CalculatorModel()
    override fun processOperator(operator: String?) {
        when (calculatorModel.state) {
            State.FIRST_NUMBER -> {
                if (operator != null) {
                    setOperation(operator)
                }
            }
            State.OPERATION -> {
                view.deleteSymbol()
                if (operator != null) {
                    setOperation(operator)
                }
            }
            else -> { }
        }
    }

    override fun processNumber(number: String?) {
        if (!number.isNullOrEmpty()) {
            when (calculatorModel.state) {
                State.EMPTY -> {
                    view.clearAll()
                    calculatorModel.state = State.FIRST_NUMBER
                    calculatorModel.firstNumber = number
                }
                State.OPERATION -> {
                    calculatorModel.state = State.SECOND_NUMBER
                    calculatorModel.secondNumber = number
                }
                State.FIRST_NUMBER -> {
                    calculatorModel.firstNumber += number
                }
                State.SECOND_NUMBER -> {
                    calculatorModel.secondNumber += number
                }
            }
        }
        view.addText(number)
    }

    override fun calculateResult() {
        when (calculatorModel.state) {
            State.FIRST_NUMBER -> {
                view.displayResult(calculatorModel.firstNumber.toDouble().toString())
            }
            State.SECOND_NUMBER -> {
                calculate()
            }
            else -> { }
        }
    }

    override fun processDel() {
        when (calculatorModel.state) {
            State.EMPTY -> {
            }
            State.OPERATION -> {
                calculatorModel.operationType = OperationType.CLEAR
                calculatorModel.state = State.FIRST_NUMBER
            }
            State.FIRST_NUMBER -> {
                calculatorModel.firstNumber = deleteNumber(calculatorModel.firstNumber)
                if (calculatorModel.firstNumber.isEmpty()) {
                    calculatorModel.state = State.EMPTY
                }
            }
            State.SECOND_NUMBER -> {
                calculatorModel.secondNumber = calculatorModel.secondNumber.cutString()
                if (calculatorModel.secondNumber.isEmpty()) {
                    calculatorModel.state = State.OPERATION
                }
            }
        }
        view.deleteSymbol()
    }

    override fun processClearing() {
        calculatorModel.firstNumber = ""
        calculatorModel.secondNumber = ""
        calculatorModel.operationType = OperationType.CLEAR
        calculatorModel.state = State.EMPTY
        view.clearAll()
    }


    private fun setOperation(operatorType: String){
        calculatorModel.operationType = operatorType.toEnum()
        calculatorModel.state = State.OPERATION
        view.addText(operatorType)
    }
    private fun calculate() {
        if (calculatorModel.firstNumber.isNotEmpty() && calculatorModel.secondNumber.isNotEmpty()) {
            var isError = false
            when (calculatorModel.operationType) {
                OperationType.PLUS -> calculatorModel.firstNumber = calculatorModel.plus()

                OperationType.MINUS -> calculatorModel.firstNumber = calculatorModel.minus()

                OperationType.MULTIPLY -> calculatorModel.firstNumber = calculatorModel.multiply()

                OperationType.DIVIDE -> {
                    calculatorModel.firstNumber = calculatorModel.divide()
                    if (calculatorModel.firstNumber == "Error") isError = true
                }
                else -> { }
            }
            calculatorModel.saveCalcRequest()
            setAnswerState(isError)
        }
    }
    private fun setAnswerState(isError: Boolean) {
        view.displayResult(calculatorModel.firstNumber)
        calculatorModel.operationType= OperationType.CLEAR
        calculatorModel.secondNumber = ""

        if (isError) {
            calculatorModel.state = State.EMPTY
            calculatorModel.firstNumber = ""
        } else calculatorModel.state = State.FIRST_NUMBER
    }

    private fun deleteNumber(number: String): String {
        val lastIndex = number.length - 1
        return if (number[lastIndex] != '.') calculatorModel.firstNumber.cutString()
        else calculatorModel.firstNumber.substring(0, lastIndex)
    }
}