package com.kushnir.app.easytofind.data.models.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListResponse<T>(
        @Json(name = "total") val total : Int,
        @Json(name = "items") val items : List<T>
)
