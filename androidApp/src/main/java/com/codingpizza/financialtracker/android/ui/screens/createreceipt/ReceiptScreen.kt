package com.codingpizza.financialtracker.android.ui.screens.createreceipt

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.codingpizza.financialtracker.android.ui.TopBar

@Composable
fun ReceiptScreen(
    viewModel: ReceiptViewModel,
    id: Long? = null,
    onClick: (String, Float) -> Unit
) {
    val state = viewModel.receiptScreenUiState.collectAsState()
    id?.let { viewModel.retrieveReceipt(id) }
    when (state.value) {
        is ReceiptUiState.Error -> {
            ReceiptContainer(
                onClick,
                uiState = state.value
            )
        }
        ReceiptUiState.Loading -> CenteredLoading()
        ReceiptUiState.Success -> ReceiptContainer(onClick, uiState = state.value)
        ReceiptUiState.Idle -> ReceiptContainer(onClick, uiState = state.value)
    }
}

@Composable
private fun ReceiptContainer(
    onClick: (String, Float) -> Unit,
    uiState: ReceiptUiState,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    name: String = "",
    amount: String = ""
) {
    var conceptName by remember { mutableStateOf(name) }
    var currentAmount by remember { mutableStateOf(amount) }

    when(uiState) {
        is ReceiptUiState.Error -> {
            LaunchSnackbarEffect(scaffoldState, uiState.errorMessage)
        }
        ReceiptUiState.Success -> {
            LaunchSnackbarEffect(scaffoldState,"Receipt Stored Successfully")
        }
        ReceiptUiState.Idle,ReceiptUiState.Loading -> {}
    }

    Scaffold(topBar = { TopBar(title = "Create Receipt") }, scaffoldState = scaffoldState) {
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

@Composable
private fun LaunchSnackbarEffect(scaffoldState: ScaffoldState, message: String) {
    LaunchedEffect(scaffoldState.snackbarHostState) {
        scaffoldState.snackbarHostState.showSnackbar(message = message)
    }
}


@Composable
fun CenteredLoading() {
    Box {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}