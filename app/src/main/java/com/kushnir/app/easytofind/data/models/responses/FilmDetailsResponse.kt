package com.kushnir.app.easytofind.data.models.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilmDetailsResponse(
        @Json(name = "data") val data : Data,
        @Json(name = "rating") val rating : FilmRatingResponse
) {
    @JsonClass(generateAdapter = true)
    data class Data (
            @Json(name = "filmId") val filmId : Int,
            @Json(name = "nameRu") val nameRu : String,
            @Json(name = "nameEn") val nameEn : String?,
            @Json(name = "webUrl") val webUrl : String,
            @Json(name = "posterUrl") val posterUrl : String,
            @Json(name = "posterUrlPreview") val posterUrlPreview : String,
            @Json(name = "year") val year : String,
            @Json(name = "filmLength") val filmLength : String?,
            @Json(name = "slogan") val slogan : String?,
            @Json(name = "description") val description : String?,
            @Json(name = "type") val type : String,
            @Json(name = "ratingMpaa") val ratingMpaa : String?,
            @Json(name = "ratingAgeLimits") val ratingAgeLimits : String?,
            @Json(name = "premiereRu") val premiereRu : String?,
            @Json(name = "distributors") val distributors : String,
            @Json(name = "premiereWorld") val premiereWorld : String?,
            @Json(name = "premiereDigital") val premiereDigital : String?,
            @Json(name = "premiereWorldCountry") val premiereWorldCountry : String?,
            @Json(name = "premiereDvd") val premiereDvd : String?,
            @Json(name = "premiereBluRay") val premiereBluRay : String?,
            @Json(name = "distributorRelease") val distributorRelease : String?,
            @Json(name = "countries") val countries : List<CountryResponse>,
            @Json(name = "genres") val genres : List<GenreResponse>,
            @Json(name = "facts") val facts : List<String>
    )
}
