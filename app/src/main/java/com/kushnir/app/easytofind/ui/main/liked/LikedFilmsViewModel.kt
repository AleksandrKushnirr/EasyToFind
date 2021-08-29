package com.kushnir.app.easytofind.ui.main.liked

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kushnir.app.easytofind.data.repositories.base.ResultWrapper
import com.kushnir.app.easytofind.domain.LikedFilmsInteractor
import com.kushnir.app.easytofind.domain.models.FilmShortModel
import kotlinx.coroutines.launch

class LikedFilmsViewModel(private val interactor: LikedFilmsInteractor) : ViewModel() {

    val filmLiveData = MutableLiveData<ResultWrapper<List<FilmShortModel>>>()
    val loadingStateLiveData = MutableLiveData<Boolean>()

    fun getLikedFilms() {
        viewModelScope.launch {
            loadingStateLiveData.postValue(true)
            filmLiveData.postValue(interactor.getAllLikedFilms())
            loadingStateLiveData.postValue(false)
        }
    }

    fun likeFilm(film: FilmShortModel) {
        viewModelScope.launch {
            interactor.saveLikedFilm(film)
        }
    }

    fun removeLike(id: Int) {
        viewModelScope.launch {
            interactor.removeLikedFilm(id)
        }
    }
}