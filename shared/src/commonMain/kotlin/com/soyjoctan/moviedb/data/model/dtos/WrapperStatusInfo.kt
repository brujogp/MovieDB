package com.soyjoctan.moviedb.data.model.dtos

sealed class WrapperStatusInfo {

    class SuccessResponse<out T>(val response: T) : WrapperStatusInfo()
    class ErrorResponse(val errorMessage: String) : WrapperStatusInfo()

    object noInternetConnection : WrapperStatusInfo()
    object loading : WrapperStatusInfo()
    object notFound : WrapperStatusInfo()
}