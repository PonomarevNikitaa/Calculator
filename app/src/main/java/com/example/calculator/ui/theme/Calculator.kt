package com.example.calculator.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val buttons = listOf(
    "C", "(", ")", "/",
    "7", "8", "9", "*",
    "4", "5", "6", "+",
    "1", "2", "3", "-",
    "AC", "0", ".", "="

)

@Composable
fun Calculator(viewModel: CalculatorViewModel) {

    val inputField = viewModel.inputField.observeAsState()
    val resultField = viewModel.resultField.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(vertical = 25.dp)
    ) {

        Column(modifier = Modifier.padding(8.dp)) {

            val inputScrollState = rememberScrollState()
            //input field
            LaunchedEffect(inputField.value) {
                val scrollDifference = inputScrollState.maxValue - inputScrollState.value
                if (scrollDifference > 0) {
                    inputScrollState.animateScrollBy(scrollDifference.toFloat())
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(inputScrollState)
            ) {
                Text(
                    text = inputField.value.orEmpty(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 34.sp,
                    textAlign = TextAlign.End,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier.wrapContentWidth()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            //result field
            Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.weight(1f),
                text = resultField.value.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 34.sp,
                lineHeight = 42.sp,
                textAlign = TextAlign.End,
                overflow = TextOverflow.Ellipsis
            )
            }

            Spacer(modifier = Modifier.weight(1f))

            //Buttons
            LazyVerticalGrid(columns = GridCells.Fixed(4)) {
                items(buttons) {
                    ButtonView(it, onClick = {
                        viewModel.onButtonClick(it)
                    })
                }
            }

        }

    }

}


@Composable
fun ButtonView(textOnButton: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(75.dp)
            .padding(8.dp)
            .border(
                2.dp,
                Color.Black, CircleShape
            )

    ) {
        FloatingActionButton(
            onClick = { onClick() },
            modifier = Modifier
                .fillMaxSize(),
            shape = CircleShape,
            contentColor = Color.White,
            containerColor = getColor(textOnButton)
        ) {

            Text(
                text = textOnButton,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

        }
    }
}

@Composable
fun getColor(textOnButton: String): Color {
    if (textOnButton == "C" || textOnButton == "AC") {
        return Color.Red
    }

    if (textOnButton == "=") {
        return Color.Green
    }
    return Color.Black
}
