package com.kushnir.app.easytofind.data.repositories

import com.kushnir.app.easytofind.data.models.entity.Films
import com.kushnir.app.easytofind.data.repositories.base.BaseRepository
import com.kushnir.app.easytofind.data.repositories.base.ResultWrapper
import com.kushnir.app.easytofind.data.room.FilmsDao
import kotlinx.coroutines.Dispatchers

class FilmsDBRepository(private val dao: FilmsDao) : BaseRepository()  {

    private val dispatcher = Dispatchers.IO

    suspend fun getAllLikedFilms(): ResultWrapper<List<Films>> {
        return safeApiCall(dispatcher) {
            dao.getAll()
        }
    }

    suspend fun saveLikedFilm(film: Films) {
        safeApiCall(dispatcher) {
            dao.insert(film)
        }
    }

    suspend fun removeLikedFilm(id: Int) {
        safeApiCall(dispatcher) {
            dao.removeById(id)
        }
    }
}