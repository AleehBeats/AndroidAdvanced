package com.example.calculator.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.calculator.R
import com.example.calculator.contract.Contract
import com.example.calculator.presenter.CalculatorPresenter

class CalculatorFragment : Fragment(), Contract.View {
    private lateinit var btnOne: Button
    private lateinit var btnTwo: Button
    private lateinit var btnThree: Button
    private lateinit var btnFour: Button
    private lateinit var btnFive: Button
    private lateinit var btnSix: Button
    private lateinit var btnSeven: Button
    private lateinit var btnEight: Button
    private lateinit var btnNine: Button
    private lateinit var btnZero: Button

    private lateinit var btnPlus: Button
    private lateinit var btnMinus: Button
    private lateinit var btnMultiply: Button
    private lateinit var btnDivide: Button
    private lateinit var btnDel: Button
    private lateinit var btnClear: Button
    private lateinit var btnDot: Button
    private lateinit var btnEqual: Button

    private lateinit var tvMain: TextView
    private val presenter: CalculatorPresenter by lazy {
        CalculatorPresenter(this)
    }
    private val numberClick = View.OnClickListener { view ->
        val number = (view as Button).text.toString()
        presenter.processNumber(number)
    }
    private val operationClick = View.OnClickListener { view ->
        val operator = (view as Button).text.toString()
        presenter.processOperator(operator)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
    }

    private fun bindViews(view: View) = with(view) {
        btnOne = findViewById(R.id.btnOne)
        btnTwo = findViewById(R.id.btnTwo)
        btnThree = findViewById(R.id.btnThree)
        btnFour = findViewById(R.id.btnFour)
        btnFive = findViewById(R.id.btnFive)
        btnSix = findViewById(R.id.btnSix)
        btnSeven = findViewById(R.id.btnSeven)
        btnEight = findViewById(R.id.btnEight)
        btnNine = findViewById(R.id.btnNine)
        btnZero = findViewById(R.id.btnZero)
        btnPlus = findViewById(R.id.btnPlus)
        btnMinus = findViewById(R.id.btnMinus)
        btnMultiply = findViewById(R.id.btnMultiply)
        btnDivide = findViewById(R.id.btnDivide)
        btnDel = findViewById(R.id.btnDel)
        btnClear = findViewById(R.id.btnClear)
        btnDot = findViewById(R.id.btnDot)
        btnEqual = findViewById(R.id.btnEqual)
        tvMain = findViewById(R.id.tvMain)

        btnZero.setOnClickListener(numberClick)
        btnOne.setOnClickListener(numberClick)
        btnTwo.setOnClickListener(numberClick)
        btnThree.setOnClickListener(numberClick)
        btnFour.setOnClickListener(numberClick)
        btnFive.setOnClickListener(numberClick)
        btnSix.setOnClickListener(numberClick)
        btnSeven.setOnClickListener(numberClick)
        btnEight.setOnClickListener(numberClick)
        btnNine.setOnClickListener(numberClick)
        btnPlus.setOnClickListener(operationClick)
        btnMinus.setOnClickListener(operationClick)
        btnDivide.setOnClickListener(operationClick)
        btnMultiply.setOnClickListener(operationClick)
        btnEqual.setOnClickListener {
            presenter.calculateResult()
        }
        btnClear.setOnClickListener {
            presenter.processClearing()
        }
        btnDel.setOnClickListener {
            presenter.processDel()
        }
    }

    override fun addText(text: String?) {
        if (!text.isNullOrEmpty()) {
            tvMain.append(text)
        }
    }

    override fun deleteSymbol() {
        if (!tvMain.text.isNullOrEmpty()) {
            tvMain.text = tvMain.text.subSequence(0, tvMain.text.length - 1)
        }
    }

    override fun clearAll() {
        tvMain.text = ""
    }

    override fun displayResult(result: String) {
        tvMain.text = result
    }


}