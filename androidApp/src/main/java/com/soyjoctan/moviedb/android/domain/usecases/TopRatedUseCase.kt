package com.soyjoctan.moviedb.android.domain.usecases

import android.util.Log
import com.soyjoctan.moviedb.android.data.network.RequestStatus
import com.soyjoctan.moviedb.android.domain.models.TopRated
import com.soyjoctan.moviedb.model.toprated.TopRatedDTO
import com.soyjoctan.moviedb.repository.Repository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TopRatedUseCase @Inject constructor() {
    private val repository: Repository = Repository()

    operator fun invoke(): Flow<RequestStatus> = flow {
        try {
            val response: TopRatedDTO = repository.getTopRated()
            val results: ArrayList<TopRated> = arrayListOf()
            response.results!!.forEach { movie ->
                results.add(
                    TopRated(
                        movieName = movie.title ?: "Sin título",
                        posterPathImage = movie.posterPath ?: "Sin imágen",
                        popularity = movie.popularity ?: 0.0
                    )
                )
            }

            emit(RequestStatus.SuccessResponse(results))

        } catch (e: java.lang.Exception) {
            Log.e("ERROR_REQUEST", e.stackTraceToString())
        }
    }
}