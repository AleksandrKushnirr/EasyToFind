package com.kushnir.app.easytofind.data.models.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountryResponse(
    @Json(name = "country") val country : String
)
