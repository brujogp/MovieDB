package com.soyjoctan.moviedb.android.data.network

sealed class RequestStatus {
    class SuccessResponse<out T>(val response: T) : RequestStatus()
    class ErrorResponse(val errorMessage: String) : RequestStatus()

    object noInternetConnection : RequestStatus()
    object loading : RequestStatus()
    object notFound : RequestStatus()
}