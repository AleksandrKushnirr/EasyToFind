package com.kushnir.app.easytofind.data.models.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StaffResponse(
        @Json(name = "staffId") val staffId : Int,
        @Json(name = "nameRu") val nameRu : String,
        @Json(name = "nameEn") val nameEn : String?,
        @Json(name = "description") val description : String?,
        @Json(name = "posterUrl") val posterUrl : String,
        @Json(name = "professionText") val professionText : String,
        @Json(name = "professionKey") val professionKey : String
)
