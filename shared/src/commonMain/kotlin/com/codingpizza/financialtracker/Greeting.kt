package com.codingpizza.financialtracker

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }

    fun dummyFlow() = flow<String> {
        delay(2000)
        emit("hello")
    }
}