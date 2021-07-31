package com.codingpizza.financialtracker.android.ui.screens.createreceipt

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codingpizza.financialtracker.android.ui.TopBar

@Composable
fun ReceiptScreen(onClick: (String, Float) -> Unit) {
    var conceptName by remember { mutableStateOf("") }
    var currentAmount by remember { mutableStateOf("") }

    Scaffold(topBar = { TopBar(title = "Create Receipt") }) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier) {
                OutlinedTextField(
                    value = conceptName,
                    onValueChange = { conceptName = it },
                    label = { Text("Concept") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                )
                OutlinedTextField(
                    value = currentAmount,
                    onValueChange = { currentAmount = it },
                    label = { Text("Amount") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            Button(
                onClick = { onClick(conceptName, currentAmount.toFloat()) },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            ) {
                Text(text = "Save Receipt")
            }
        }
    }
}


@Preview(
    showBackground = true
)
@Composable
fun ReceiptScreenPreview() {
    ReceiptScreen(onClick = { a, b -> })
}