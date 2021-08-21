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
    receiptId: String?,
    onClick: (String, Float, String?) -> Unit,
) {
    val state by viewModel.receiptScreenUiState.collectAsState()
    when (state) {
        ReceiptUiState.Idle -> {
            viewModel.initialize(receiptId)
        }
        ReceiptUiState.Loading -> {
            CenteredLoading()
        }
        else -> ReceiptContainer(onClick, uiState = state, currentReceiptId = receiptId)
    }
}

@Composable
private fun ReceiptContainer(
    onClick: (String, Float, String?) -> Unit,
    uiState: ReceiptUiState,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    currentReceiptId: String? = null
) {
    val conceptNameByState = when (uiState) {
        is ReceiptUiState.SuccessRetrievingReceipt -> uiState.receipt.concept
        ReceiptUiState.Success -> ""
        else -> ""
    }
    val conceptAmountByState = when (uiState) {
        is ReceiptUiState.SuccessRetrievingReceipt -> uiState.receipt.amount.toString()
        ReceiptUiState.Success -> ""
        else -> ""
    }
    var conceptName by remember { mutableStateOf(conceptNameByState) }
    var currentAmount by remember { mutableStateOf(conceptAmountByState) }

    when (uiState) {
        is ReceiptUiState.Error -> {
            LaunchSnackbarEffect(scaffoldState, uiState.errorMessage)
        }
        ReceiptUiState.Success -> {
            LaunchSnackbarEffect(scaffoldState, "Receipt Stored Successfully")
        }
        ReceiptUiState.Idle, ReceiptUiState.Loading -> {
        }
        is ReceiptUiState.SuccessRetrievingReceipt -> {
        }
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
                onClick = { onClick(conceptName, currentAmount.toFloat(), currentReceiptId) },
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