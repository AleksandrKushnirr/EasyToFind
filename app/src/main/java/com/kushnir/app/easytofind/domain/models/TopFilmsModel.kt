package com.kushnir.app.easytofind.domain.models

data class TopFilmsModel(
    val pagesCount : Int,
    val films: List<FilmShortModel>
)
