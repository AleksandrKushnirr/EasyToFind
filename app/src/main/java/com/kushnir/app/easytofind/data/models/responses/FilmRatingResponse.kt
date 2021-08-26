package com.kushnir.app.easytofind.data.models.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilmRatingResponse(
        @Json(name = "rating") val rating : String,
        @Json(name = "ratingVoteCount") val ratingVoteCount : Int?,
        @Json(name = "ratingImdb") val ratingImdb : String?,
        @Json(name = "ratingImdbVoteCount") val ratingImdbVoteCount : Int?,
        @Json(name = "ratingFilmCritics") val ratingFilmCritics : String?,
        @Json(name = "ratingFilmCriticsVoteCount") val ratingFilmCriticsVoteCount : Int?,
        @Json(name = "ratingAwait") val ratingAwait : String?,
        @Json(name = "ratingAwaitCount") val ratingAwaitCount : Int?,
        @Json(name = "ratingRfCritics") val ratingRfCritics : String?,
        @Json(name = "ratingRfCriticsVoteCount") val ratingRfCriticsVoteCount : Int?
)
