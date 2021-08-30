package com.kushnir.app.easytofind.domain

import com.kushnir.app.easytofind.data.models.entity.Films
import com.kushnir.app.easytofind.data.repositories.FilmsDBRepository
import com.kushnir.app.easytofind.data.repositories.FilmsRepository
import com.kushnir.app.easytofind.data.repositories.base.ResultWrapper
import com.kushnir.app.easytofind.domain.enums.RatingColor
import com.kushnir.app.easytofind.domain.models.CastModel
import com.kushnir.app.easytofind.domain.models.FilmDetailsModel
import com.kushnir.app.easytofind.domain.models.ImageModel
import com.kushnir.app.easytofind.domain.models.SimilarFilmModel
import kotlin.math.roundToInt

class FilmDetailsInteractor(
        private val repository: FilmsRepository,
        private val dbRepository: FilmsDBRepository
) {

    companion object {
        private const val PROFESSION_KEY_ACTOR = "ACTOR"
    }

    suspend fun getDetailsByFilmId(id: Int): ResultWrapper<FilmDetailsModel> {
        val likedFilms = dbRepository.getAllLikedFilms()
        val response = repository.getFilmDetails(id)
        if (response is ResultWrapper.Success) {
            return ResultWrapper.Success(
                    FilmDetailsModel(
                            filmId = response.value.data.filmId,
                            name = response.value.data.nameRu,
                            webUrl = response.value.data.webUrl,
                            poster = ImageModel(
                                    image = response.value.data.posterUrl,
                                    preview = response.value.data.posterUrlPreview
                            ),
                            year = response.value.data.year,
                            filmLength = response.value.data.filmLength,
                            description = response.value.data.description,
                            type = response.value.data.type,
                            ratingAgeLimits = response.value.data.ratingAgeLimits,
                            countries = response.value.data.countries.map { it.country },
                            genres = response.value.data.genres.map { it.genre },
                            rating = getDoubleRating(response.value.rating.rating, response.value.rating.ratingAwait ?: "null"),
                            ratingColor = RatingColor.fromDoubleValue(getDoubleRating(response.value.rating.rating, response.value.rating.ratingAwait ?: "null")),
                            isLiked = getIsLikedByLikedListAndFilmId(likedFilms, response.value.data.filmId)
                    )
            )
        } else {
            return response as ResultWrapper.Error
        }
    }

    suspend fun getFilmImagesByFilmId(id: Int): ResultWrapper<List<ImageModel>> {
        val response = repository.getFilmImages(id)
        return if (response is ResultWrapper.Success) {
            ResultWrapper.Success(
                    response.value.frames.take(15).map {
                        ImageModel(
                                image = it.image,
                                preview = it.preview
                        )
                    }
            )
        } else {
            response as ResultWrapper.Error
        }
    }

    suspend fun getCastByFilmId(id: Int): ResultWrapper<List<CastModel>> {
        val response = repository.getFilmStaff(id)
        return if (response is ResultWrapper.Success) {
            ResultWrapper.Success(response.value.filter { it.professionKey == PROFESSION_KEY_ACTOR }.map {
                CastModel(
                        name = it.nameRu,
                        role = it.description ?: "",
                        posterUrl = it.posterUrl
                )
            })
        } else {
            response as ResultWrapper.Error
        }
    }

    suspend fun getSimilarFilmsByFilmId(id: Int): ResultWrapper<List<SimilarFilmModel>> {
        val response = repository.getSimilarFilmsByFilmId(id)
        return if (response is ResultWrapper.Success) {
            ResultWrapper.Success(
                    response.value.items.map {
                        SimilarFilmModel(
                                filmId = it.filmId,
                                name = it.nameRu,
                                image = ImageModel(
                                        image = it.posterUrl,
                                        preview = it.posterUrlPreview
                                )
                        )
                    }
            )
        } else {
            response as ResultWrapper.Error
        }
    }

    private fun getDoubleRating(rating: String, ratingAwait: String): Double {
        return if (rating != "0" && rating != "0.0") {
            rating.toDouble()
        } else {
            if (ratingAwait != "null") {
                ratingAwait.toDouble().roundToInt()/10.0
            } else {
                0.0
            }
        }
    }

    private fun getIsLikedByLikedListAndFilmId(result: ResultWrapper<List<Films>>, id: Int): Boolean {
        if (result is ResultWrapper.Success) {
            result.value.forEach { if (it.id == id) return true }
        }
        return false
    }
}