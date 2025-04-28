package com.example.calculator.ui.theme

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udojava.evalex.Expression

class CalculatorViewModel : ViewModel() {

    private val _inputField = MutableLiveData("")
    val inputField = _inputField

    private val _resultField = MutableLiveData("")
    val resultField = _resultField

    fun onButtonClick(btnText: String) {

        Log.i("Button \"${btnText}\" was clicked", btnText)

        _inputField.value?.let {
            // Если нажали AC - очистить все
            if (btnText == "AC") {
                _inputField.value = ""
                _resultField.value = ""
                return
            }

            // Если нажали C - удалить последний символ
            if (btnText == "C") {
                if (it.isNotEmpty()) {
                    _inputField.value = it.substring(0, it.length - 1)
                    _resultField.value = ""
                }
                return
            }

            // Если нажали "=", вычисляем результат
            if (btnText == "=") {
                val result = calculateResult(it)
                _resultField.value = formatResult(result)
                return
            }

            // Для всех остальных кнопок просто добавляем символ в inputField
            _inputField.value = it + btnText
        }
    }

    // Функция для вычисления результата
    private fun calculateResult(value: String): String {
        try {
            val result = Expression(value).eval()
            return result.toString()
        } catch (e: Exception) {
            return "" // Если произошла ошибка (например, неправильный формат), выводим ошибку
        }
    }

    // Функция для форматирования результата
    private fun formatResult(result: String): String {
        return try {
            val num = result.toDouble()

            if(num < -9200000000000000000 || num > 9200000000000000000){
                return num.toBigDecimal().toString()
            }

            // Преобразуем число в строку, если оно целое, убираем ".0"
            if (num % 1 == 0.0) {
                return num.toLong().toString() // Конвертируем в Int, чтобы убрать ".0"
            }

            return num.toString() // В противном случае оставляем как есть
        } catch (e: Exception) {
            result // В случае ошибки просто возвращаем исходный результат
        }
    }
}
