package com.codingpizza.financialtracker.android.ui.screens.createreceipt

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.codingpizza.financialtracker.android.ui.TopBar
import org.koin.androidx.compose.getViewModel

@Composable
fun receiptScreen(
    viewModel: ReceiptViewModel = getViewModel(),
    receiptId: String?) {
    val state by viewModel.receiptScreenUiState.collectAsState()
    when (state) {
        ReceiptUiState.Loading -> centeredLoading()
        else -> receiptContainer(uiState = state,
            currentReceiptId = receiptId,
            onClick = { concept, amount, currentId  -> viewModel.storeReceipt(concept,amount.toDouble(),currentId) },
            onReceiptRequest = { id -> viewModel.initialize(id) }
        )
    }
}

@Composable
private fun receiptContainer(
    uiState: ReceiptUiState,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    currentReceiptId: String? = null,
    onClick: (String, Float, String?) -> Unit,
    onReceiptRequest: (String) -> Unit
) {
    var conceptName by remember { mutableStateOf("") }
    var currentAmount by remember { mutableStateOf("") }

    when (uiState) {
        is ReceiptUiState.Error -> {
            launchSnackbarEffect(scaffoldState, uiState.errorMessage)
        }
        ReceiptUiState.SuccessStoringReceipt -> {
            conceptName = ""
            currentAmount = ""
            launchSnackbarEffect(scaffoldState, "Receipt Stored Successfully")
        }
        ReceiptUiState.Idle -> {
            currentReceiptId?.let {
                onReceiptRequest(it)
            }
        }
        ReceiptUiState.Loading -> {
            //no-op
        }
        is ReceiptUiState.SuccessRetrievingReceipt -> {
            conceptName = uiState.receipt.concept
            currentAmount = uiState.receipt.amount.toString()
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
private fun launchSnackbarEffect(scaffoldState: ScaffoldState, message: String) {
    LaunchedEffect(scaffoldState.snackbarHostState) {
        scaffoldState.snackbarHostState.showSnackbar(message = message)
    }
}


@Composable
fun centeredLoading() {
    Box {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}