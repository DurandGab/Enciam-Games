package com.example.enciamgames

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class RawgResponse(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: List<JeuVideo>
)

@Serializable
data class JeuVideo(
    val id: Int,
    val slug: String,
    val name: String,
    val released: String? = null,
    val tba: Boolean,
    val background_image: String? = null,
    val rating: Double,
    val rating_top: Int,
    val ratings: JsonElement? = null,
    val ratings_count: Int,
    val reviews_text_count: Int? = null,
    val added: Int,
    val added_by_status: JsonElement? = null,
    val metacritic: Int? = null,
    val playtime: Int,
    val suggestions_count: Int,
    val updated: String,
    val esrb_rating: EsrbRating? = null,
    val platforms: List<PlatformInfo>? = null
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
    val esrb_rating: EsrbRating? = null,
    val platforms: List<PlatformInfo>? = null
)

@Serializable
data class EsrbRating(
    val id: Int,
    val slug: String,
    val name: String
)

@Serializable
data class PlatformInfo(
    val platform: Platform,
    val released_at: String? = null,
    val requirements: Requirements? = null
)

@Serializable
data class Platform(
    val id: Int,
    val slug: String,
    val name: String
)

@Serializable
data class Requirements(
    val minimum: String? = null,
    val recommended: String? = null
)

