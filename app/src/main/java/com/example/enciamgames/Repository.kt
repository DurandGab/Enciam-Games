package com.example.enciamgames

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.http.HttpMethod
import io.ktor.serialization.kotlinx.json.json


class MonRepository {
    val client = HttpClient(CIO) {

        install(ContentNegotiation) {
            json(kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("Ktor-Logger", message)
                }
            }
        }
    }

    private val baseUrl = "https://api.rawg.io/api/"
    private val apiKey = "cf8fc9f536424dcf857db9a1fe89592c"

    suspend fun getJeuxVideos(page: Int = 1, pageSize: Int = 20): List<JeuVideo> {
        val response: RawgResponse = client.request("${baseUrl}games") {
            method = HttpMethod.Get
            parameter("key", apiKey)
            parameter("page", page)
            parameter("page_size", pageSize)
        }.body()

        return response.results
    }

    suspend fun getDetailJeuVideo(id: Int): DetailJeuVideo {
        val response: DetailJeuVideo = client.request("${baseUrl}games/$id") {
            method = HttpMethod.Get
            parameter("key", apiKey)
        }.body()

        return response
    }

    suspend fun rechercheJeuxVideosParNom(nom: String, page: Int): List<JeuVideo> {
        val response: RawgResponse = client.request("${baseUrl}games") {
            method = HttpMethod.Get
            parameter("key", apiKey)
            parameter("search", nom)
            parameter("page", page)
        }.body()

        return response.results
    }
}