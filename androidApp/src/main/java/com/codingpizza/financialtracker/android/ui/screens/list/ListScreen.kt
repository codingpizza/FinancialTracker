package com.codingpizza.financialtracker.android.ui.screens.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codingpizza.financialtracker.Receipt
import com.codingpizza.financialtracker.android.ui.TopBar

@Composable
fun ListScreen(viewModel: ListViewModel, onClick: (ReceiptClickedState) -> Unit) {
    val state by viewModel.uiState.collectAsState()
    when (state) {
        ListUiState.Error -> Text(text = "Ha ocurrido un error")
        ListUiState.Loading -> {
            viewModel.retrieveReceipts()
            CircularProgressIndicator()
        }
        is ListUiState.Success -> ReceiptList(
            receiptList = (state as ListUiState.Success).receiptList,
            onClick = onClick
        )
    }
}

@Composable
private fun ReceiptList(receiptList: List<Receipt>, onClick: (ReceiptClickedState) -> Unit) {
    Scaffold(
        floatingActionButton = { CreateReceiptFab(onClick) },
        topBar = { TopBar(title = "Your Receipts") }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            items(items = receiptList) { item ->
                ReceiptRow(item) {
                    onClick(it)
                }
            }
        }
    }
}

@Composable
private fun CreateReceiptFab(onClick: (ReceiptClickedState.NewReceiptState) -> Unit = {}) {
    FloatingActionButton(onClick = { onClick(ReceiptClickedState.NewReceiptState) }) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add a new Receipt")
    }
}

@Composable
private fun ReceiptRow(
    item: Receipt,
    onReceiptClick: (ReceiptClickedState.ModifyReceiptState) -> Unit
) {
    Card(
        elevation = 4.dp, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(60.dp)
                .clickable(onClick = { onReceiptClick(ReceiptClickedState.ModifyReceiptState(item.id)) })
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
