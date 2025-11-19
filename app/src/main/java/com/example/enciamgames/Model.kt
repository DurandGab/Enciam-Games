package com.example.enciamgames

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

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
)

@Serializable
data class DetailJeuVideo(
    val id: Int,
    val slug: String,
    val name: String,
    val name_original: String? = null,
    val description: String? = null,
    val metacritic: Int? = null,
    val released: String? = null,
    val tba: Boolean,
    val updated: String? = null,
    val background_image: String? = null,
    val reactions: JsonElement? = null,
    val added: Int,
    val playtime: Int,
    val screenshots_count: Int,
    val movies_count: Int,
    val creators_count: Int,
    val achievements_count: Int,
    val reddit_url: String? = null,
    val reddit_name: String? = null,
    val reddit_description: String? = null,
    val reddit_logo: String? = null,
    val reddit_count: Int? = null,
    val ratings_count: Int,
    val suggestions_count: Int,
    val parents_count: Int,
    val additions_count: Int,
    val game_series_count: Int,
)

@Entity
data class JeuVideoFavori(
    val jeu: JeuVideo,
    @PrimaryKey val id: Int,
) {

}