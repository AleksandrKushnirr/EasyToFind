package com.kushnir.app.easytofind.ui.main.list_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kushnir.app.easytofind.data.repositories.base.ResultWrapper
import com.kushnir.app.easytofind.domain.TopFilmsInteractor
import com.kushnir.app.easytofind.domain.models.TopFilmsModel
import kotlinx.coroutines.launch

class FilmsListViewModel(private val interactor: TopFilmsInteractor) : ViewModel() {

    val filmsLiveData = MutableLiveData<ResultWrapper<TopFilmsModel>>()
    val loadingStateLiveData = MutableLiveData<Boolean>()

    fun getFilms(type: String, page: Int) {
        viewModelScope.launch {
            if (page == 1) loadingStateLiveData.postValue(true)
            when(type) {
                FilmsListFragment.BEST_FILMS_LIST_TYPE -> filmsLiveData.postValue(interactor.getBestFilms(page))
                FilmsListFragment.POPULAR_FILMS_LIST_TYPE -> filmsLiveData.postValue(interactor.getPopularFilms(page))
                FilmsListFragment.AWAIT_FILMS_LIST_TYPE -> filmsLiveData.postValue(interactor.getAwaitFilms(page))
            }
            loadingStateLiveData.postValue(false)
        }
    }
}