package com.codingpizza.financialtracker.android.ui.screens.list

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.codingpizza.financialtracker.Receipt
import com.codingpizza.financialtracker.android.ui.TopBar
import org.koin.androidx.compose.getViewModel

@ExperimentalMaterialApi
@Composable
fun ListScreen(viewModel: ListViewModel = getViewModel(), onClick: (ReceiptClickedState) -> Unit) {
    val state by viewModel.uiState.collectAsState()
    when (state) {
        ListUiState.Error -> Text(text = "Ha ocurrido un error")
        ListUiState.Loading -> {
            viewModel.retrieveReceipts()
            CircularProgressIndicator()
        }
        is ListUiState.Success -> ReceiptList(
            receiptList = (state as ListUiState.Success).receiptList,
            onClick = onClick,
            onItemRemoved = { removedReceipt ->
                viewModel.removeReceipt(removedReceipt)
            }
        )
    }
}

@ExperimentalMaterialApi
@Composable
private fun ReceiptList(
    receiptList: List<Receipt>,
    onClick: (ReceiptClickedState) -> Unit,
    onItemRemoved: (Receipt) -> Unit
) {
    Scaffold(
        floatingActionButton = { CreateReceiptFab(onClick) },
        topBar = { TopBar(title = "Your Receipts") }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            items(items = receiptList) { item ->
                NewReceiptListItem(
                    receipt = item,
                    onReceiptClick = { onClick(ReceiptClickedState.ModifyReceiptState(item.id)) },
                    onReceiptDeleted = onItemRemoved
                )
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

@ExperimentalMaterialApi
@Composable
fun NewReceiptListItem(
    receipt: Receipt,
    onReceiptClick: (ReceiptClickedState.ModifyReceiptState) -> Unit,
    onReceiptDeleted: (Receipt) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ListItem(
        text = {
            Text(text = receipt.concept)
        },
        secondaryText = {
            Text(text = "${receipt.amount}$")
        },
        trailing = {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Delete item")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(onClick = { onReceiptDeleted(receipt) }) {
                    Text("Delete")
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .clickable {
                onReceiptClick(ReceiptClickedState.ModifyReceiptState(receipt.id))
            }
    )
}