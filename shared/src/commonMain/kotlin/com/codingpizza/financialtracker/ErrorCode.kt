package com.codingpizza.financialtracker

sealed class ErrorCode {
    data class ServerError(val errorCode: Int) : ErrorCode()
    object InternalError : ErrorCode()
}