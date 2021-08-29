package com.kushnir.app.easytofind.domain

import com.kushnir.app.easytofind.data.models.entity.Films
import com.kushnir.app.easytofind.data.repositories.FilmsDBRepository
import com.kushnir.app.easytofind.data.repositories.FilmsRepository
import com.kushnir.app.easytofind.data.repositories.base.ResultWrapper
import com.kushnir.app.easytofind.domain.enums.RatingColor
import com.kushnir.app.easytofind.domain.models.FilmShortModel
import com.kushnir.app.easytofind.domain.models.ImageModel
import com.kushnir.app.easytofind.domain.models.TopFilmsModel
import kotlinx.coroutines.delay

class TopFilmsInteractor(
        private val repository: FilmsRepository,
        private val dbRepository: FilmsDBRepository
) {

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
                            poster = ImageModel(
                                    image = response.posterUrl,
                                    preview = response.posterUrlPreview
                            )
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
        val likedFilms = dbRepository.getAllLikedFilms()
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
                                    poster = ImageModel(
                                            image = it.posterUrl,
                                            preview = it.posterUrlPreview
                                    ),
                                    ratingColor = RatingColor.fromDoubleValue(getDoubleRatingByStringRating(it.rating)),
                                    isLiked = getIsLikedByLikedListAndFilmId(likedFilms, it.filmId)
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
                            poster = ImageModel(
                                    image = response.posterUrl,
                                    preview = response.posterUrlPreview
                            )
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
        val likedFilms = dbRepository.getAllLikedFilms()
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
                                        poster = ImageModel(
                                                image = it.posterUrl,
                                                preview = it.posterUrlPreview
                                        ),
                                        ratingColor = RatingColor.fromDoubleValue(getDoubleRatingByStringRating(it.rating)),
                                        isLiked = getIsLikedByLikedListAndFilmId(likedFilms, it.filmId)
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
                            poster = ImageModel(
                                    image = response.posterUrl,
                                    preview = response.posterUrlPreview
                            )
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
        val likedFilms = dbRepository.getAllLikedFilms()
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
                                        poster = ImageModel(
                                                image = it.posterUrl,
                                                preview = it.posterUrlPreview
                                        ),
                                        ratingColor = RatingColor.fromDoubleValue(getDoubleRatingByStringRating(it.rating)),
                                        isLiked = getIsLikedByLikedListAndFilmId(likedFilms, it.filmId)
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

    private fun getIsLikedByLikedListAndFilmId(result: ResultWrapper<List<Films>>, id: Int): Boolean {
        if (result is ResultWrapper.Success) {
            result.value.forEach { if (it.id == id) return true }
        }
        return false
    }
}