package com.codingpizza.financialtracker.android

import android.app.Application
import com.codingpizza.financialtracker.android.di.appModule
import com.codingpizza.financialtracker.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class FinancialTrackerApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@FinancialTrackerApplication)
            modules(appModule)
        }
    }
}