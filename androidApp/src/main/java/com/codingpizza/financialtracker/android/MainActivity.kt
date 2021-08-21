package com.codingpizza.financialtracker.android

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codingpizza.financialtracker.android.navigation.Destinations
import com.codingpizza.financialtracker.android.ui.screens.createreceipt.ReceiptScreen
import com.codingpizza.financialtracker.android.ui.screens.createreceipt.ReceiptViewModel
import com.codingpizza.financialtracker.android.ui.screens.list.ListScreen
import com.codingpizza.financialtracker.android.ui.screens.list.ListViewModel
import com.codingpizza.financialtracker.android.ui.screens.list.ReceiptClickedState

class MainActivity : AppCompatActivity() {

    private val receiptViewModel by viewModels<ReceiptViewModel>()
    private val listViewModel by viewModels<ListViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Destinations.ListScreen.route
                ) {
                    composable(route = Destinations.ListScreen.route) {
                        listViewModel.retrieveReceipts()
                        ListScreen(
                            listViewModel.uiState,
                            onClick = { receiptClicked ->
                                val id = when (receiptClicked) {
                                    is ReceiptClickedState.ModifyReceiptState -> {
                                        val route = Destinations.ReceiptScreen.createRoute(receiptClicked.id)
                                        Log.d("Route","La ruta es: $route")
                                        route
                                    }
                                    ReceiptClickedState.NewReceiptState -> {
                                        val route = Destinations.ReceiptScreen.route
                                        Log.d("Route","La ruta es: $route")
                                        route
                                    }
                                }
                                navController.navigate(route = id)
                            })
                    }
                    composable(route = Destinations.ReceiptScreen.route) { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("receiptId")
                        Log.d("Route","Id disponible: $id")
                        ReceiptScreen(viewModel = receiptViewModel,receiptId = id) { concept, amount,currentId  ->
                            Log.d("Clicked", "Stored $concept and $amount")
                            receiptViewModel.storeReceipt(concept,amount.toDouble(),currentId)
                        }
                    }
                }
            }
        }
    }
}
