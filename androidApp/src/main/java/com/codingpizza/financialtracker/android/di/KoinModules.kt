package com.codingpizza.financialtracker.android.di

import com.codingpizza.financialtracker.android.ui.screens.createreceipt.ReceiptViewModel
import com.codingpizza.financialtracker.android.ui.screens.list.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { ReceiptViewModel(get()) }
    viewModel { ListViewModel(get()) }
}
