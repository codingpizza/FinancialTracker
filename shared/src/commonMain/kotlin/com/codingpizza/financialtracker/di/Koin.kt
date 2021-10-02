package com.codingpizza.financialtracker.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(clientModule(enableNetworkLogs = enableNetworkLogs), platformModule())
    }

fun initKoin() = initKoin(enableNetworkLogs = false) {}