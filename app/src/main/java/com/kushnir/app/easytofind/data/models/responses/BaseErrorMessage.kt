package com.kushnir.app.easytofind.data.models.responses

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class BaseErrorMessage(
        @Json(name = "error_messages") val errorMessages : List<String>?,
        @Json(name = "error_message") val errorMessage : String?,
        @Json(name = "errors") val errors : List<String>?
) : Parcelable

// TODO подкорректировать потом исходя из ошибок моего сервера
