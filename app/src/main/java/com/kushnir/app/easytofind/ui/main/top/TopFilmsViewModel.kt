package com.kushnir.app.easytofind.ui.main.top

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kushnir.app.easytofind.data.repositories.base.ResultWrapper
import com.kushnir.app.easytofind.domain.TopFilmsInteractor
import com.kushnir.app.easytofind.domain.models.FilmShortModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class TopFilmsViewModel(private val topFilmsInteractor: TopFilmsInteractor) : ViewModel() {

    val topBestFilmsLiveData = MutableLiveData<ResultWrapper<List<FilmShortModel>>>()
    val topPopularFilmsLiveData = MutableLiveData<ResultWrapper<List<FilmShortModel>>>()
    val topAwaitFilmsLiveData = MutableLiveData<ResultWrapper<List<FilmShortModel>>>()
    val loadingStateLiveData = MutableLiveData<Boolean>()

    fun getContent() {
        viewModelScope.launch {
            loadingStateLiveData.postValue(true)

            val bestFilmsRequest = async { topBestFilmsLiveData.postValue(topFilmsInteractor.getRandomBestFilms()) }
            val popularFilmsRequest = async { topPopularFilmsLiveData.postValue(topFilmsInteractor.getRandomPopularFilms()) }
            val awaitFilmsRequest = async { topAwaitFilmsLiveData.postValue(topFilmsInteractor.getRandomAwaitFilms()) }
            awaitAll(bestFilmsRequest, popularFilmsRequest, awaitFilmsRequest)

            loadingStateLiveData.postValue(false)
        }
    }
}