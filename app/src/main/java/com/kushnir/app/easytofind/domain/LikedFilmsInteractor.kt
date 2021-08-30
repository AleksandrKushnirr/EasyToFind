package com.kushnir.app.easytofind.domain

import com.kushnir.app.easytofind.data.models.entity.Films
import com.kushnir.app.easytofind.data.repositories.FilmsDBRepository
import com.kushnir.app.easytofind.data.repositories.base.ResultWrapper
import com.kushnir.app.easytofind.domain.models.FilmShortModel

class LikedFilmsInteractor(private val repository: FilmsDBRepository) {

    suspend fun getAllLikedFilms(): ResultWrapper<List<FilmShortModel>> {
        val result = repository.getAllLikedFilms()
        return if (result is ResultWrapper.Success) {
            ResultWrapper.Success(result.value.map {
                FilmShortModel(
                        id = it.id,
                        name = it.name,
                        year = it.year,
                        filmLength = it.filmLength,
                        countries = it.countries,
                        genres = it.genres,
                        rating = it.rating,
                        poster = it.poster,
                        ratingColor = it.ratingColor,
                        isLiked = true
                )
            })
        } else {
            result as ResultWrapper.Error
        }
    }

    suspend fun saveLikedFilm(film: FilmShortModel) {
        repository.saveLikedFilm(Films(
                id = film.id,
                name = film.name,
                year = film.year,
                filmLength = film.filmLength,
                countries = film.countries,
                genres = film.genres,
                rating = film.rating,
                poster = film.poster,
                ratingColor = film.ratingColor
        ))
    }

    suspend fun removeLikedFilm(id: Int) {
        repository.removeLikedFilm(id)
    }
}