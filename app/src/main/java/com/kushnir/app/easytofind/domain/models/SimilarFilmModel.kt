package com.kushnir.app.easytofind.domain.models

import com.squareup.moshi.Json

data class SimilarFilmModel(
        val filmId : Int,
        val name : String,
        val image: ImageModel
)
