package com.kushnir.app.easytofind.ui.main.top

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kushnir.app.easytofind.data.repositories.base.ResultWrapper
import com.kushnir.app.easytofind.domain.FilmsInteractor
import com.kushnir.app.easytofind.domain.models.FilmShortModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class TopFilmsViewModel(private val filmsInteractor: FilmsInteractor) : ViewModel() {

    val topBestFilmsLiveData = MutableLiveData<ResultWrapper<List<FilmShortModel>>>()
    val topPopularFilmsLiveData = MutableLiveData<ResultWrapper<List<FilmShortModel>>>()
    val topAwaitFilmsLiveData = MutableLiveData<ResultWrapper<List<FilmShortModel>>>()
    val loadingStateLiveData = MutableLiveData<Boolean>()

    fun getContent() {
        viewModelScope.launch {
            loadingStateLiveData.postValue(true)

            val bestFilmsRequest = async { topBestFilmsLiveData.postValue(filmsInteractor.getRandomBestFilms()) }
            val popularFilmsRequest = async { topPopularFilmsLiveData.postValue(filmsInteractor.getRandomPopularFilms()) }
            val awaitFilmsRequest = async { topAwaitFilmsLiveData.postValue(filmsInteractor.getRandomAwaitFilms()) }
            awaitAll(bestFilmsRequest, popularFilmsRequest, awaitFilmsRequest)

            loadingStateLiveData.postValue(false)
        }
    }
}