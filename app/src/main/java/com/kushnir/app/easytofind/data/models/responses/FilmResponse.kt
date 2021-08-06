package com.kushnir.app.easytofind.data.models.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilmResponse(
    @Json(name = "filmId") val filmId : Int,
    @Json(name = "nameRu") val nameRu : String,
    @Json(name = "nameEn") val nameEn : String?,
    @Json(name = "year") val year : String,
    @Json(name = "filmLength") val filmLength : String?,
    @Json(name = "countries") val countries : List<CountryResponse>,
    @Json(name = "genres") val genres : List<GenreResponse>,
    @Json(name = "rating") val rating : String,
    @Json(name = "ratingVoteCount") val ratingVoteCount : Int,
    @Json(name = "posterUrl") val posterUrl : String,
    @Json(name = "posterUrlPreview") val posterUrlPreview : String,
    @Json(name = "ratingChange") val ratingChange : String?
)
