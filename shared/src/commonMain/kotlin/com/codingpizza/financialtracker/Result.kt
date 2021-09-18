package com.codingpizza.financialtracker

sealed class Result<out T> {
    data class Success<out T>(val data : T) : Result<T>()
    data class Error(val errorMessage: String,val errorCode : ErrorCode) : Result<Nothing>()
}
