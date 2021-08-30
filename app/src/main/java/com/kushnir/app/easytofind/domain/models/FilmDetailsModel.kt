package com.kushnir.app.easytofind.domain.models

import com.kushnir.app.easytofind.domain.enums.RatingColor

data class FilmDetailsModel(
        val filmId : Int,
        val name : String,
        val webUrl : String,
        val poster: ImageModel,
        val year : String,
        val filmLength : String?,
        val description : String?,
        val type : String,
        val ratingAgeLimits : String?,
        val countries : List<String>,
        val genres : List<String>,
        val rating : Double,
        val ratingColor: RatingColor? = null,
        val isLiked: Boolean
)
