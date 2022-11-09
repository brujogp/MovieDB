package com.soyjoctan.moviedb.data.repository

import com.soyjoctan.moviedb.data.constants.GenresEnum
import com.soyjoctan.moviedb.data.repository.requests.*
import io.ktor.client.*
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

    suspend fun getGenres(): HttpResponse {
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
            GenresEnum.ACTION.genreId -> {
                GenresEnum.ACTION.movieId
            }
            GenresEnum.ADVENTURE.genreId -> {
                GenresEnum.ADVENTURE.movieId
            }
            GenresEnum.ANIMATION.genreId -> {
                GenresEnum.ANIMATION.movieId
            }
            GenresEnum.COMEDY.genreId -> {
                GenresEnum.COMEDY.movieId
            }
            GenresEnum.CRIME.genreId -> {
                GenresEnum.CRIME.movieId
            }
            GenresEnum.DOCUMENTARY.genreId -> {
                GenresEnum.DOCUMENTARY.movieId
            }
            GenresEnum.DRAMA.genreId -> {
                GenresEnum.DRAMA.movieId
            }
            GenresEnum.FAMILY.genreId -> {
                GenresEnum.FAMILY.movieId
            }
            GenresEnum.FANTASY.genreId -> {
                GenresEnum.FANTASY.movieId
            }
            GenresEnum.HISTORY.genreId -> {
                GenresEnum.HISTORY.movieId
            }
            GenresEnum.HORROR.genreId -> {
                GenresEnum.HORROR.movieId
            }
            GenresEnum.MUSIC.genreId -> {
                GenresEnum.MUSIC.movieId
            }
            GenresEnum.MYSTERY.genreId -> {
                GenresEnum.MYSTERY.movieId
            }
            GenresEnum.ROMANCE.genreId -> {
                GenresEnum.ROMANCE.movieId
            }
            GenresEnum.SCIENCE_FICTION.genreId -> {
                GenresEnum.SCIENCE_FICTION.movieId
            }
            GenresEnum.TV_MOVIES.genreId -> {
                GenresEnum.TV_MOVIES.movieId
            }
            GenresEnum.THRILLER.genreId -> {
                GenresEnum.THRILLER.movieId
            }
            GenresEnum.WAR.genreId -> {
                GenresEnum.WAR.movieId
            }
            GenresEnum.WESTERN.genreId -> {
                GenresEnum.WESTERN.movieId
            }
            else -> {
                0L
            }
        }

        return client.get(resource = FindByGenresRequest.Id(movieId = movieId))
    }

    suspend fun getMovieDetailById(movieId: Long): HttpResponse {
        return client.get(resource = MovieDetailsRequest.Id(movieId = movieId))
    }
}