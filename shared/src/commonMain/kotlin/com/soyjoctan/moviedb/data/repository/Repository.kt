package com.soyjoctan.moviedb.data.repository

import com.soyjoctan.moviedb.data.constants.GenresEnum
import com.soyjoctan.moviedb.data.model.genres.Genre
import com.soyjoctan.moviedb.data.model.genres.GenresDTO
import com.soyjoctan.moviedb.data.model.toprated.TopRatedDTO
import com.soyjoctan.moviedb.data.model.upcoming.UpComingMoviesDTO
import com.soyjoctan.moviedb.data.repository.requests.FindByGenres
import com.soyjoctan.moviedb.data.repository.requests.GenresRequest
import com.soyjoctan.moviedb.data.repository.requests.TopRatedRequest
import com.soyjoctan.moviedb.data.repository.requests.UpComingMoviesRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

class Repository {
    private val client = HttpClient {
        install(Resources)
        install(ContentNegotiation) {
            json()
        }
        install(DefaultRequest)
        defaultRequest {
            // Para iOS, el host no debe tener ningún segmento además del host
            host = "api.themoviedb.org"
            url {
                parameters.append("api_key", "c32a3b714b45efcc4f83833f9f955d5d")
                parameters.append("language", "es-MX")
                protocol = URLProtocol.HTTPS
            }
            headers {
                append(HttpHeaders.Accept, "application/json")
            }
        }
    }

    suspend fun getGenres(): HttpResponse{
        return client.get(resource = GenresRequest())
    }

    suspend fun getTopRated(): HttpResponse {
        return client.get(resource = TopRatedRequest())
    }

    suspend fun getUpcomingMovies(): HttpResponse {
        return client.get(resource = UpComingMoviesRequest())
    }

    suspend fun getMoviesByGenre(genreId: Long): HttpResponse {
        val movieId: Long = when (genreId) {
            GenresEnum.ANIMATION.genreId -> {
                GenresEnum.ANIMATION.movieId
            }
            GenresEnum.HORROR.genreId -> {
                GenresEnum.HORROR.movieId
            }
            else -> {
                0L
            }
        }

        return client.get(resource = FindByGenres.Id(movieId = movieId))
    }
}