package com.example.enciamgames

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable

@Serializable
data class RawgResponse(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: List<JeuVideo>
)
@JsonClass(generateAdapter = true)
@Serializable
data class JeuVideo(
    val id: Int,
    val name: String,
    val released: String? = null,
    val background_image: String? = null,
    val metacritic: Int? = null,
    val playtime: Int? = null,
)

@Serializable
data class DetailJeuVideo(
    val id: Int,
    val name: String,
    val description: String? = null,
    val metacritic: Int? = null,
    val released: String? = null,
    val background_image: String? = null,
    val playtime: Int? = null,
)

@Entity
data class JeuVideoFavori(
    val jeu: JeuVideo,
    @PrimaryKey val id: Int,
) {

}