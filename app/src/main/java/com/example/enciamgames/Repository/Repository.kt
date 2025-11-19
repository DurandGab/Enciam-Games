package com.example.enciamgames.Repository

import android.app.Application
import android.util.Log
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Room
import com.example.enciamgames.DetailJeuVideo
import com.example.enciamgames.JeuVideo
import com.example.enciamgames.JeuVideoFavori
import com.example.enciamgames.RawgResponse
import com.squareup.moshi.Moshi
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
import kotlinx.serialization.json.Json


class MonRepository (application: Application){
    val client = HttpClient(CIO) {

        install(ContentNegotiation) {
            json(Json {
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

    val database = Room.databaseBuilder( application, AppDatabase::class.java, "database-name" )
        .fallbackToDestructiveMigration(false)
        .addTypeConverter(Converters(Moshi.Builder().build()))
        .build()
    val dao = database.jeuxVideoFavoriDao()

    private val baseUrl = "https://api.rawg.io/api/"
    private val apiKey = "da4ae3be44204cae9a284d7a5373bc83"

    suspend fun getJeuxVideosLesMieuxNotes(page: Int = 1, pageSize: Int = 20): List<JeuVideo> {
        val response: RawgResponse = client.request("${baseUrl}games") {
            method = HttpMethod.Get
            parameter("key", apiKey)
            parameter("page", page)
            parameter("page_size", pageSize)
            parameter("ordering", "-metacritic")
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
            parameter("ordering", "-metacritic")
        }.body()

        return response.results
    }

    fun getJeuxVideosFavoris(): List<JeuVideoFavori>{
        val favori = dao.getFavoris()
        return favori
    }

    fun ajouterJeuVideoFavori(jeuvideo: JeuVideoFavori){
        val jeuxvideofavori = JeuVideoFavori(id = jeuvideo.id, name = jeuvideo.name, released = jeuvideo.released, background_image = jeuvideo.background_image, metacritic = jeuvideo.metacritic)
        return dao.ajouterFavori(jeuxvideofavori)
    }

    fun supprimerJeuVideoFavori(id: Int){
        dao.supprimerFavoriById(id)
    }
}