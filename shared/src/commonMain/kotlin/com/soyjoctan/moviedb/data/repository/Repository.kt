package com.soyjoctan.moviedb.data.repository

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

    suspend fun getGenres(): HttpResponse? {
        val finalRequest = try {
            client.get(resource = GenresRequest())
        } catch (e: Throwable) {
            print(e.stackTraceToString())
            null
        }

        return finalRequest
    }

    suspend fun getTopRated(): HttpResponse? {
        val finalResponse =
            try {
                client.get(resource = TopRatedRequest())
            } catch (e: Throwable) {
                print(e.stackTraceToString())
                null
            }

        return finalResponse
    }

    suspend fun getUpcomingMovies(): HttpResponse? {
        val finalResponse = try {
            client.get(resource = UpComingMoviesRequest())
        } catch (e: Throwable) {
            print(e.stackTraceToString())
            null
        }
        return finalResponse
    }

    suspend fun getMoviesByGenre(genreId: Long, page: Long): HttpResponse? {
        val finalRequest = try {
            client.get(
                resource = DiscoverMovies(
                    withGenres = genreId,
                    page = page,
                    includeAdult = null
                )
            )
        } catch (e: Throwable) {
            print(e.stackTraceToString())
            null
        }

        return finalRequest
    }

    suspend fun getMovieDetailById(movieId: Long): HttpResponse? {
        val finalResponse = try {
            client.get(resource = MovieDetailsRequest.Id(movieId = movieId))
        } catch (e: Throwable) {
            print(e.stackTraceToString())
            null
        }

        return finalResponse
    }


    suspend fun getPopularTVShows(): HttpResponse? {
        val finalResponse = try {
            client.get(resource = PopularTvShowsRequest())
        } catch (e: Throwable) {
            print(e.stackTraceToString())
            null
        }

        return finalResponse
    }
}