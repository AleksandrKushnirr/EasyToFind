package com.kushnir.app.easytofind.domain

import com.kushnir.app.easytofind.data.repositories.FilmsRepository
import com.kushnir.app.easytofind.data.repositories.base.ResultWrapper
import com.kushnir.app.easytofind.domain.models.FilmShortModel
import kotlinx.coroutines.delay

class FilmsInteractor(private val repository: FilmsRepository) {

    suspend fun getRandomBestFilms(): ResultWrapper<List<FilmShortModel>> {
        val firstPageResponse = repository.getTopBestFilms(1)
        if (firstPageResponse is ResultWrapper.Success) {
            val pagesCount = firstPageResponse.value.pagesCount
            val randomPageResponse = repository.getTopBestFilms((1..pagesCount).random())
            if (randomPageResponse is ResultWrapper.Success) {
                return ResultWrapper.Success(randomPageResponse.value.films.map { response ->
                    FilmShortModel(
                            id = response.filmId,
                            name = response.nameRu,
                            year = response.year,
                            filmLength = response.filmLength,
                            countries = response.countries.map { it.country },
                            genres = response.genres.map { it.genre },
                            rating = response.rating,
                            posterUrl = response.posterUrl
                    )
                }.shuffled())
            } else {
                return randomPageResponse as ResultWrapper.Error
            }
        } else {
            return firstPageResponse as ResultWrapper.Error
        }
    }

    suspend fun getRandomPopularFilms(): ResultWrapper<List<FilmShortModel>> {
        val firstPageResponse = repository.getTopPopularFilms(1)
        if (firstPageResponse is ResultWrapper.Success) {
            val pagesCount = firstPageResponse.value.pagesCount
            val randomPageResponse = repository.getTopPopularFilms((1..pagesCount).random())
            if (randomPageResponse is ResultWrapper.Success) {
                return ResultWrapper.Success(randomPageResponse.value.films.map { response ->
                    FilmShortModel(
                            id = response.filmId,
                            name = response.nameRu,
                            year = response.year,
                            filmLength = response.filmLength,
                            countries = response.countries.map { it.country },
                            genres = response.genres.map { it.genre },
                            rating = response.rating,
                            posterUrl = response.posterUrl
                    )
                }.shuffled())
            } else {
                return randomPageResponse as ResultWrapper.Error
            }
        } else {
            return firstPageResponse as ResultWrapper.Error
        }
    }

    suspend fun getRandomAwaitFilms(): ResultWrapper<List<FilmShortModel>> {
        val firstPageResponse = repository.getTopAwaitFilms(1)
        if (firstPageResponse is ResultWrapper.Success) {
            val pagesCount = firstPageResponse.value.pagesCount
            val randomPageResponse = repository.getTopAwaitFilms((1..pagesCount).random())
            if (randomPageResponse is ResultWrapper.Success) {
                return ResultWrapper.Success(randomPageResponse.value.films.map { response ->
                    FilmShortModel(
                            id = response.filmId,
                            name = response.nameRu,
                            year = response.year,
                            filmLength = response.filmLength,
                            countries = response.countries.map { it.country },
                            genres = response.genres.map { it.genre },
                            rating = response.rating,
                            posterUrl = response.posterUrl
                    )
                }.shuffled())
            } else {
                return randomPageResponse as ResultWrapper.Error
            }
        } else {
            return firstPageResponse as ResultWrapper.Error
        }
    }
}