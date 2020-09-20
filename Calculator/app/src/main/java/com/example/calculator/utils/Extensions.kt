package com.example.calculator.utils

import com.example.calculator.model.OperationType

fun String.toEnum(): OperationType {
    return when (this) {
        "+" -> OperationType.PLUS
        "-" -> OperationType.MINUS
        "/" -> OperationType.DIVIDE
        "*" -> OperationType.MULTIPLY
        else -> OperationType.CLEAR
    }
}

fun String.cutString(): String {
    val string = this.toDouble().toString()
    return if (string.isNotEmpty()) {
        string.substring(0, string.length - 1)
    } else ""
}