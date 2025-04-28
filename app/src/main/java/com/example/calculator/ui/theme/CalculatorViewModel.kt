package com.example.calculator.ui.theme

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorViewModel : ViewModel() {

    private val _inputField = MutableLiveData("")
    val inputField = _inputField

    private val _resultField = MutableLiveData("")
    val resultField = _resultField

    fun onButtonClick(btnText: String) {

        Log.i("Button \"${btnText}\" was clicked", btnText)

        _inputField.value?.let {

            if (btnText == "AC") {
                _inputField.value = ""
                _resultField.value = ""
                return
            }


            if (btnText == "C") {
                if (it.isNotEmpty()) {
                    _inputField.value = it.substring(0, it.length - 1)
                    _resultField.value = ""
                }
                return
            }


            if (btnText == "=") {
                val result = calculateResult(it)
                _resultField.value = formatResult(result)
                return
            }


            _inputField.value = it + btnText
        }
    }


    private fun calculateResult(value: String): String {
        return try {
            val expression = ExpressionBuilder(value).build()
            val result = expression.evaluate()

            result.toBigDecimal().stripTrailingZeros().toPlainString()
        } catch (_: Exception) {
            ""
        }
    }


    private fun formatResult(result: String): String {
        return try {
            val num = result.toBigDecimal()

            if (num.stripTrailingZeros().scale() <= 0) {
                num.toBigInteger().toString()
            } else {
                num.stripTrailingZeros().toPlainString()
            }
        } catch (_: Exception) {
            result
        }
    }
}
