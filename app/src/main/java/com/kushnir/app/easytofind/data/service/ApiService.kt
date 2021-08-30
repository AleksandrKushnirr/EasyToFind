package com.kushnir.app.easytofind.data.service

import com.kushnir.app.easytofind.data.models.responses.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/api/v2.2/films/top")
    suspend fun getTopFilms(
        @Query("type") type: String,
        @Query("page") page: Int
    ): TopFilmsResponse

    @GET("/api/v2.1/films/{id}")
    suspend fun getFilmDetailsById(
            @Path("id") filmId: Int,
            @Query("append_to_response") append: String = "RATING"
    ): FilmDetailsResponse

    @GET("/api/v1/staff")
    suspend fun getStaffByFilmId(
            @Query("filmId") filmId: Int
    ): List<StaffResponse>

    @GET("/api/v2.2/films/{id}/similars")
    suspend fun getSimilarsByFilmId(
            @Path("id") filmId: Int
    ): ListResponse<SimilarFilmsResponse>

    @GET("/api/v2.1/films/{id}/frames")
    suspend fun getImagesByFilmId(
            @Path("id") filmId: Int
    ): FilmImagesResponse
}