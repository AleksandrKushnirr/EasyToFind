package com.kushnir.app.easytofind.domain

import com.kushnir.app.easytofind.data.repositories.FilmsRepository
import com.kushnir.app.easytofind.data.repositories.base.ResultWrapper
import com.kushnir.app.easytofind.domain.enums.RatingColor
import com.kushnir.app.easytofind.domain.models.FilmShortModel
import com.kushnir.app.easytofind.domain.models.TopFilmsModel
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

    suspend fun getBestFilms(page: Int): ResultWrapper<TopFilmsModel> {
        delay(1000)
        val response = repository.getTopBestFilms(page)
        if (response is ResultWrapper.Success) {
            return ResultWrapper.Success(
                    TopFilmsModel(
                        pagesCount = response.value.pagesCount,
                        films = response.value.films.map {
                            FilmShortModel(
                                    id = it.filmId,
                                    name = it.nameRu,
                                    year = it.year,
                                    filmLength = it.filmLength,
                                    countries = it.countries.map { it.country },
                                    genres = it.genres.map { it.genre },
                                    rating = getDoubleRatingByStringRating(it.rating).toString(),
                                    posterUrl = it.posterUrl,
                                    ratingColor = RatingColor.fromDoubleValue(getDoubleRatingByStringRating(it.rating))
                            )
                        }
                    )
            )
        } else {
            return response as ResultWrapper.Error
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

    suspend fun getPopularFilms(page: Int): ResultWrapper<TopFilmsModel> {
        delay(1000)
        val response = repository.getTopPopularFilms(page)
        if (response is ResultWrapper.Success) {
            return ResultWrapper.Success(
                    TopFilmsModel(
                            pagesCount = response.value.pagesCount,
                            films = response.value.films.map {
                                FilmShortModel(
                                        id = it.filmId,
                                        name = it.nameRu,
                                        year = it.year,
                                        filmLength = it.filmLength,
                                        countries = it.countries.map { it.country },
                                        genres = it.genres.map { it.genre },
                                        rating = getDoubleRatingByStringRating(it.rating).toString(),
                                        posterUrl = it.posterUrl,
                                        ratingColor = RatingColor.fromDoubleValue(getDoubleRatingByStringRating(it.rating))
                                )
                            }
                    )
            )
        } else {
            return response as ResultWrapper.Error
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

    suspend fun getAwaitFilms(page: Int): ResultWrapper<TopFilmsModel> {
        delay(1000)
        val response = repository.getTopAwaitFilms(page)
        if (response is ResultWrapper.Success) {
            return ResultWrapper.Success(
                    TopFilmsModel(
                            pagesCount = response.value.pagesCount,
                            films = response.value.films.map {
                                FilmShortModel(
                                        id = it.filmId,
                                        name = it.nameRu,
                                        year = it.year,
                                        filmLength = it.filmLength,
                                        countries = it.countries.map { it.country },
                                        genres = it.genres.map { it.genre },
                                        rating = getDoubleRatingByStringRating(it.rating).toString(),
                                        posterUrl = it.posterUrl,
                                        ratingColor = RatingColor.fromDoubleValue(getDoubleRatingByStringRating(it.rating))
                                )
                            }
                    )
            )
        } else {
            return response as ResultWrapper.Error
        }
    }

    private fun getDoubleRatingByStringRating(rating: String): Double {
        return if (rating.contains("%")) {
            rating.replace("%", "").toDouble()/10
        } else {
            rating.toDouble()
        }
    }
}