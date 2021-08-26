package com.kushnir.app.easytofind.data.models.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilmImagesResponse(
        @Json(name = "frames") val frames : List<Image>
) {
    @JsonClass(generateAdapter = true)
    data class Image(
            @Json(name = "image") val image : String,
            @Json(name = "preview") val preview : String
    )
}
