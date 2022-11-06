package com.soyjoctan.moviedb.repository

import com.soyjoctan.moviedb.model.GenresDTO
import com.soyjoctan.moviedb.repository.requests.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.*
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
                protocol = URLProtocol.HTTPS
            }
            headers {
                append(HttpHeaders.Accept, "application/json")
            }
        }
    }

    suspend fun getGenres(): GenresDTO {
        return client.get(resource = Genres()).body()
    }
}