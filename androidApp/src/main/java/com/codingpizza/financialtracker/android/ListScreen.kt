package com.codingpizza.financialtracker.android

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codingpizza.financialtracker.Receipt
import com.codingpizza.financialtracker.android.ui.TopBar
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ListScreen(uiState: StateFlow<ListUiState>, onClick: () -> Unit) {
    val state = uiState.collectAsState()
    when (state.value) {
        ListUiState.Error -> Text(text = "Ha ocurrido un error")
        ListUiState.Loading -> CircularProgressIndicator()
        is ListUiState.Success -> ReceiptList(
            (state.value as ListUiState.Success).receiptList,
            onClick = { onClick() })
    }
}

@Composable
private fun ReceiptList(receiptList: List<Receipt>, onClick: () -> Unit = { }) {
    Scaffold(
        floatingActionButton = { CreateReceiptFab(onClick) },
        topBar = { TopBar(title = "Your Receipts") }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            items(items = receiptList) { item ->
                ReceiptRow(item)
            }
        }
    }
}

@Composable
private fun CreateReceiptFab(onClick: () -> Unit = {}) {
    FloatingActionButton(onClick = { onClick() }) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add a new Receipt")
    }
}

@Composable
private fun ReceiptRow(item: Receipt) {
    Card(
        elevation = 4.dp, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(60.dp)
        ) {
            Text(
                text = item.concept, modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .weight(0.8f)
            )
            Text(
                text = "${item.amount}$", modifier = Modifier
                    .padding(end = 16.dp, top = 16.dp)
            )
        }
    }
}