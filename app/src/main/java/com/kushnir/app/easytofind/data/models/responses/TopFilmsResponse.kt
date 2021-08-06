package com.kushnir.app.easytofind.data.models.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopFilmsResponse(
    @Json(name = "pagesCount") val pagesCount : Int,
    @Json(name = "films") val films : List<FilmResponse>
)
