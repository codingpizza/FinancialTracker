package com.codingpizza.financialtracker.android

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codingpizza.financialtracker.Receipt

@Composable
fun ListScreen(viewModel: ListViewModel) {
    val state = viewModel.uiState.collectAsState()
    viewModel.retrieveReceipts()
    when (state.value) {
        ListUiState.Error -> Text(text = "Ha ocurrido un error")
        ListUiState.Loading -> CircularProgressIndicator()
        is ListUiState.Success -> ReceiptList((state.value as ListUiState.Success).receiptList)
    }
}

@Composable
private fun ReceiptList(receiptList: List<Receipt>) {
    Scaffold {
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
private fun ReceiptRow(item: Receipt) {
    Card(
        elevation = 4.dp, modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
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
                    .weight(0.1f)
            )
        }
    }
}