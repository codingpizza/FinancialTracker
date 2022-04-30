package com.codingpizza.financialtracker.android.ui.screens.list

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.codingpizza.financialtracker.ErrorCode
import com.codingpizza.financialtracker.Receipt
import com.codingpizza.financialtracker.android.R
import com.codingpizza.financialtracker.android.ui.TopBar
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.koin.androidx.compose.getViewModel

@ExperimentalMaterialApi
@Composable
fun ListScreen(viewModel: ListViewModel = getViewModel(), onClick: (ReceiptClickedState) -> Unit) {
    val state by viewModel.uiState.collectAsState()
    val onRefresh = { viewModel.updateReceipts() }
    val onItemRemoved: (Receipt) -> Unit = { removedReceipt -> viewModel.removeReceipt(removedReceipt) }
    when (state) {
        is ListUiState.Error -> {
            EmptyPatternContainer(
                title = (state as ListUiState.Error).mapErrorMessage(),
                drawableId = R.drawable.errorimage
            )
        }
        ListUiState.Loading -> {
            viewModel.retrieveReceipts()
            CircularProgressIndicator()
        }
        is ListUiState.Success -> {
            ReceiptList(
                receiptList = (state as ListUiState.Success).receiptList,
                onClick = onClick,
                onItemRemoved = onItemRemoved,
                onRefresh = onRefresh,
                isRefreshing = false
            )
        }
        is ListUiState.IsRefreshing -> {
            ReceiptList(
                receiptList = (state as ListUiState.IsRefreshing).updatedList,
                onClick = onClick,
                onItemRemoved = onItemRemoved,
                onRefresh = { },
                isRefreshing = true
            )
        }
        is ListUiState.UpdateSuccessful -> {
            ReceiptList(
                receiptList = (state as ListUiState.UpdateSuccessful).updatedList,
                onClick = onClick,
                onItemRemoved = onItemRemoved,
                onRefresh = onRefresh,
                isRefreshing = false
            )
        }
    }.also {
        Log.d("State","State $state")
    }
}

@ExperimentalMaterialApi
@Composable
private fun ReceiptList(
    receiptList: List<Receipt>,
    onClick: (ReceiptClickedState) -> Unit,
    onItemRemoved: (Receipt) -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: Boolean
) {
    Scaffold(
        floatingActionButton = { CreateReceiptFab(onClick) },
        topBar = { TopBar(title = "Your Receipts") }) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = {
                Log.d("Composable List Screen","OnRefresh $isRefreshing")
                onRefresh()
            }) {
            if (receiptList.isEmpty()) {
                EmptyPatternContainer(title = "It's empty here. Create a new Receipt!",drawableId = R.drawable.emptypatternimage)
            } else {
                ListContent(receiptList, onClick, onItemRemoved)
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun ListContent(
    receiptList: List<Receipt>,
    onClick: (ReceiptClickedState) -> Unit,
    onItemRemoved: (Receipt) -> Unit
) {
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
                DropdownMenuItem(onClick = {
                    Log.d("Composable List Screen","Delete clicked $receipt")
                    expanded = false
                    onReceiptDeleted(receipt)
                }) {
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

@Composable
fun EmptyPatternContainer(title: String, @DrawableRes drawableId : Int) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Image(
                painter = painterResource(drawableId),
                contentDescription = "Empty pattern image",
            )
            Text(
                text = "Illustration by Pixeltrue from Ouch!",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

private fun ListUiState.Error.mapErrorMessage(): String {
    return when (errorCode) {
        ErrorCode.InternalError -> "An error has occurred."
        is ErrorCode.ServerError -> "We are having problems connecting with our servers, please try again later."
    }
}