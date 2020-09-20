package com.example.calculator.contract

interface Contract {
    interface View {
        fun addText(text: String?)
        fun deleteSymbol()
        fun clearAll()
        fun displayResult(result: String)
    }

    interface Presenter {
        fun processOperator(operator: String?)
        fun processNumber(number: String?)
        fun calculateResult()
        fun processDel()
        fun processClearing()
    }

    interface Model {
        fun plus(): String
        fun minus(): String
        fun multiply(): String
        fun divide(): String
    }
}