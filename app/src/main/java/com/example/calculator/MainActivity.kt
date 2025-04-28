package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.calculator.ui.theme.Calculator
import com.example.calculator.ui.theme.CalculatorTheme
import com.example.calculator.ui.theme.CalculatorViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = CalculatorViewModel()

        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Calculator(viewModel)
                }
            }
        }
    }
}
