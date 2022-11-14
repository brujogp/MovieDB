package com.soyjoctan.moviedb.domain.use_cases

import com.soyjoctan.moviedb.data.model.dtos.WrapperStatusInfo
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

inline fun <reified DtoType> basicValidationResponse(response: HttpResponse?): Flow<WrapperStatusInfo> {
    return flow {
        try {
            emit(WrapperStatusInfo.Loading)

            when (response?.status) {
                HttpStatusCode.OK -> {
                    emit(WrapperStatusInfo.SuccessResponse<DtoType>(response.body()))
                }
                HttpStatusCode.BadGateway -> {
                    emit(WrapperStatusInfo.ErrorResponse("Error en el servidor"))
                }
                HttpStatusCode.NotFound -> {
                    emit(WrapperStatusInfo.NotFound)
                }
                null -> {
                    emit(WrapperStatusInfo.NoInternetConnection)
                }
            }

        } catch (e: Exception) {
            print(e.stackTraceToString())
        }
    }
}
