package com.soyjoctan.moviedb.data.model.dtos

sealed class WrapperStatusRequest {

    class SuccessResponse<out T>(val response: T) : WrapperStatusRequest()
    class ErrorResponse(val errorMessage: String) : WrapperStatusRequest()

    object noInternetConnection : WrapperStatusRequest()
    object loading : WrapperStatusRequest()
    object notFound : WrapperStatusRequest()
}