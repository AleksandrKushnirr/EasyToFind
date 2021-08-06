package com.kushnir.app.easytofind.data.repositories

import com.kushnir.app.easytofind.data.models.responses.TopFilmsResponse
import com.kushnir.app.easytofind.data.repositories.base.BaseRepository
import com.kushnir.app.easytofind.data.repositories.base.ResultWrapper
import com.kushnir.app.easytofind.data.service.ApiService
import kotlinx.coroutines.Dispatchers

class FilmsRepository(private val service: ApiService) : BaseRepository() {

    companion object{
        private const val TOP_250_BEST_FILMS = "TOP_250_BEST_FILMS"
        private const val TOP_100_POPULAR_FILMS = "TOP_100_POPULAR_FILMS"
        private const val TOP_AWAIT_FILMS = "TOP_AWAIT_FILMS"
    }

    private val dispatcher = Dispatchers.IO

    suspend fun getTopBestFilms(page: Int): ResultWrapper<TopFilmsResponse> {
        return safeApiCall(dispatcher) {
            service.getTopFilms(TOP_250_BEST_FILMS, page)
        }
    }

    suspend fun getTopPopularFilms(page: Int): ResultWrapper<TopFilmsResponse> {
        return safeApiCall(dispatcher) {
            service.getTopFilms(TOP_100_POPULAR_FILMS, page)
        }
    }

    suspend fun getTopAwaitFilms(page: Int): ResultWrapper<TopFilmsResponse> {
        return safeApiCall(dispatcher) {
            service.getTopFilms(TOP_AWAIT_FILMS, page)
        }
    }
}