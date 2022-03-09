package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private var lastAction = false
    private var lastDigit = true
    private var lastPoint = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        initUI()
    }

    private fun initUI() {
        button_0.setOnClickListener { isLastDigit("0") }
        button_1.setOnClickListener { isLastDigit("1") }
        button_2.setOnClickListener { isLastDigit("2") }
        button_3.setOnClickListener { isLastDigit("3") }
        button_4.setOnClickListener { isLastDigit("4") }
        button_5.setOnClickListener { isLastDigit("5") }
        button_6.setOnClickListener { isLastDigit("6") }
        button_7.setOnClickListener { isLastDigit("7") }
        button_8.setOnClickListener { isLastDigit("8") }
        button_9.setOnClickListener { isLastDigit("9") }

        button_ac.setOnClickListener {
            top_text.text = ""
            bottom_text.text = ""
        }
        button_backspace.setOnClickListener {
            val splice = top_text.text
            if(splice.isNotEmpty()){
                top_text.text = splice.substring(0, splice.length - 1)
            }
            bottom_text.text = ""
        }
        button_comma.setOnClickListener { isLastPoint(",") }
        button_divide.setOnClickListener { isLastOperator("÷") }
        button_multiply.setOnClickListener { isLastOperator("×") }
        button_minus.setOnClickListener { isLastOperator("-") }
        button_plus.setOnClickListener { isLastOperator("+") }
        button_equals.setOnClickListener { calculate() }
    }

    private fun calculate() {
        try {
            val expression = ExpressionBuilder(top_text.text.toString().replace(Regex("×"), "*").replace(
                Regex("÷"), "/").replace(Regex(","), ".")).build()
            val result = expression.evaluate()
            if (result == result.toLong().toDouble()){
                bottom_text.text = result.toLong().toString()
            }
            else{
                bottom_text.text = result.toString()
            }
        } catch (e:Exception) {
            bottom_text.text = "ERROR"
        }
    }

    private fun isLastPoint(str: String) {
        if(lastPoint){
            setTopText(str)
            lastPoint = false
            lastDigit = true
            lastAction = false
        }
    }

    private fun isLastOperator(str: String) {
        if(lastAction) {
            setTopText(str)
            lastAction = false
            lastPoint = false
            lastDigit = true
        }
    }

    private fun isLastDigit(str: String) {
        if(lastDigit) {
            setTopText(str)
            lastDigit = true
            lastPoint = true
            lastAction = true
        }
    }

    private fun setTopText(str: String) {
        if (bottom_text.text.isNotEmpty()) {
            top_text.text = bottom_text.text.toString()
        }
        top_text.append(str)
    }
}