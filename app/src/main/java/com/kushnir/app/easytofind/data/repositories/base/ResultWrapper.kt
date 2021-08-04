package com.kushnir.app.easytofind.data.repositories.base

import com.kushnir.app.easytofind.data.models.responses.BaseErrorMessage

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(val code: Int? = null, val error: BaseErrorMessage?, val throwableMessage: String?) : ResultWrapper<Nothing>()
}
