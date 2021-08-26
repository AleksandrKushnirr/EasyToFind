package com.kushnir.app.easytofind.ui.main.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kushnir.app.easytofind.data.repositories.base.ResultWrapper
import com.kushnir.app.easytofind.domain.FilmDetailsInteractor
import com.kushnir.app.easytofind.domain.models.CastModel
import com.kushnir.app.easytofind.domain.models.FilmDetailsModel
import com.kushnir.app.easytofind.domain.models.ImageModel
import com.kushnir.app.easytofind.domain.models.SimilarFilmModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class FilmDetailsViewModel(private val interactor: FilmDetailsInteractor) : ViewModel() {

    val filmDetailsLiveData = MutableLiveData<ResultWrapper<FilmDetailsModel>>()
    val filmImagesLiveData = MutableLiveData<ResultWrapper<List<ImageModel>>>()
    val filmCastLiveData = MutableLiveData<ResultWrapper<List<CastModel>>>()
    val filmSimilarFilmsLiveData = MutableLiveData<ResultWrapper<List<SimilarFilmModel>>>()
    val loadingStateLiveData = MutableLiveData<Boolean>()

    fun getFilmDetailsData(id: Int) {
        viewModelScope.launch {
            loadingStateLiveData.postValue(true)

            val detailsRequest = async { filmDetailsLiveData.postValue(interactor.getDetailsByFilmId(id)) }
            val imagesRequest = async { filmImagesLiveData.postValue(interactor.getFilmImagesByFilmId(id)) }
            val castRequest = async { filmCastLiveData.postValue(interactor.getCastByFilmId(id)) }
            val similarFilmsRequest = async { filmSimilarFilmsLiveData.postValue(interactor.getSimilarFilmsByFilmId(id)) }
            awaitAll(detailsRequest, imagesRequest, castRequest, similarFilmsRequest)

            loadingStateLiveData.postValue(false)
        }
    }
}