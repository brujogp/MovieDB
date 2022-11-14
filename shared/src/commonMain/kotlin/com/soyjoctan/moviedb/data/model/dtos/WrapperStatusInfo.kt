package com.soyjoctan.moviedb.data.model.dtos

sealed class WrapperStatusInfo {

    class SuccessResponse<out T>(val response: T) : WrapperStatusInfo()
    class ErrorResponse(val errorMessage: String) : WrapperStatusInfo()

    object NoInternetConnection : WrapperStatusInfo()
    object Loading : WrapperStatusInfo()
    object NotFound : WrapperStatusInfo()
    object SuccessTransaction: WrapperStatusInfo()
}