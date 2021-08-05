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

class MainActivity : AppCompatActivity() {

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
                        val viewModel by viewModels<ListViewModel>()
                        viewModel.retrieveReceipts()
                        ListScreen(
                            viewModel.uiState,
                            onClick = { navController.navigate(Destinations.ReceiptScreen.route) })
                    }
                    composable(route = Destinations.ReceiptScreen.route) {
                        val viewModel by viewModels<ReceiptViewModel>()
                        ReceiptScreen(viewModel = viewModel) { concept, amount ->
                            Log.d("Clicked", "Stored $concept and $amount")
                            viewModel.storeReceipt(concept,amount.toDouble())
                        }
                    }
                }
            }
        }
    }
}
