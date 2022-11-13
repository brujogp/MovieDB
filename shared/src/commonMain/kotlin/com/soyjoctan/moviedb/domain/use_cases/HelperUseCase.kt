package com.soyjoctan.moviedb.domain.use_cases

import com.soyjoctan.moviedb.data.model.dtos.WrapperStatusRequest
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

inline fun <reified DtoType> basicValidationResponse(response: HttpResponse?): Flow<WrapperStatusRequest> {
    return flow {
        try {
            emit(WrapperStatusRequest.loading)

            when (response?.status) {
                HttpStatusCode.OK -> {
                    emit(WrapperStatusRequest.SuccessResponse<DtoType>(response.body()))
                }
                HttpStatusCode.BadGateway -> {
                    emit(WrapperStatusRequest.ErrorResponse("Error en el servidor"))
                }
                HttpStatusCode.NotFound -> {
                    emit(WrapperStatusRequest.notFound)
                }
                null -> {
                    emit(WrapperStatusRequest.noInternetConnection)
                }
            }

        } catch (e: Exception) {
            print(e.stackTraceToString())
        }
    }
}
