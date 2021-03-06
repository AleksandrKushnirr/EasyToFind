package com.kushnir.app.easytofind.domain.models

import com.kushnir.app.easytofind.domain.enums.RatingColor

data class FilmShortModel(
    val id : Int,
    val name : String,
    val year : String,
    val filmLength : String?,
    val countries : List<String>,
    val genres : List<String>,
    val rating : String,
    val poster : ImageModel,
    val ratingColor: RatingColor? = null,
    var isLiked: Boolean = false
)
