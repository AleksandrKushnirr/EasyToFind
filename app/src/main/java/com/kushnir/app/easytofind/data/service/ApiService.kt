package com.kushnir.app.easytofind.data.service

import com.kushnir.app.easytofind.data.models.responses.TopFilmsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/api/v2.2/films/top")
    suspend fun getTopFilms(
        @Query("type") type: String,
        @Query("page") page: Int
    ): TopFilmsResponse
}