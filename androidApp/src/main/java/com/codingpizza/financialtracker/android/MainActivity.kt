package com.codingpizza.financialtracker.android

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val viewModel by viewModels<ListViewModel>()
                viewModel.retrieveReceipts()
                ListScreen(viewModel.uiState,onClick = { Log.d("OnClick","Has hecho click") })
            }
        }
    }
}
