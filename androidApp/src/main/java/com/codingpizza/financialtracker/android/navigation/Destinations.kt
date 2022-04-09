package com.codingpizza.financialtracker.android.navigation

sealed class Destinations(val route: String) {
    object ListScreen : Destinations(route = "ListScreen")
    object ReceiptScreen : Destinations(route = "ReceiptScreen?receiptId={receiptId}") {
        fun createRoute(id: String) : String = "ReceiptScreen?receiptId=$id"
    }
}
