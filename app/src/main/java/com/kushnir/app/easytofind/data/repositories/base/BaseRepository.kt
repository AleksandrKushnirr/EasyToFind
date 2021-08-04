package com.kushnir.app.easytofind.data.repositories.base

import com.kushnir.app.easytofind.data.models.responses.BaseErrorMessage
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException

open class BaseRepository {

    suspend fun <T> safeApiCall(
            dispatcher: CoroutineDispatcher,
            apiCall: suspend () -> T
    ): ResultWrapper<T> {
        return withContext(dispatcher) {
            try {
                ResultWrapper.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse = convertErrorBody(throwable)

                        ResultWrapper.Error(code, errorResponse, throwable.message())
                    }
                    is ConnectException -> {
                        ResultWrapper.Error(null, null, "ConnectException")
                    }
                    else -> {
                        throwable.printStackTrace()
                        ResultWrapper.Error(null, null, throwable.message)
                    }
                }
            }
        }
    }

    private fun convertErrorBody(throwable: HttpException): BaseErrorMessage? {
        return try {
            throwable.response()?.errorBody()?.string()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(BaseErrorMessage::class.java)
                moshiAdapter.fromJson(it)
            }
        } catch (exception: Exception) {
            null
        }
    }
}