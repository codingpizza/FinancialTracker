package com.codingpizza.financialtracker.android

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.codingpizza.financialtracker.android.navigation.Destinations
import com.codingpizza.financialtracker.android.ui.screens.createreceipt.receiptScreen
import com.codingpizza.financialtracker.android.ui.screens.list.ListScreen
import com.codingpizza.financialtracker.android.ui.screens.list.ReceiptClickedState

@ExperimentalMaterialApi
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
                        ListScreen { receiptClicked ->
                            val id = when (receiptClicked) {
                                is ReceiptClickedState.ModifyReceiptState -> {
                                    val route =
                                        Destinations.ReceiptScreen.createRoute(receiptClicked.id.toString())
                                    Log.d("Route", "La ruta es: $route")
                                    route
                                }
                                ReceiptClickedState.NewReceiptState -> {
                                    val route = Destinations.ReceiptScreen.route
                                    Log.d("Route", "La ruta es: $route")
                                    route
                                }
                            }
                            navController.navigate(route = id)
                        }
                    }
                    composable(
                        route = Destinations.ReceiptScreen.route,
                        arguments = listOf(navArgument("receiptId") {
                            nullable = true
                        })
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("receiptId")
                        Log.d("Route", "Id disponible: $id")
                        receiptScreen(receiptId = id)
                    }
                }
            }
        }
    }
}
