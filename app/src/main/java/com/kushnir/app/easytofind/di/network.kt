package com.kushnir.app.easytofind.di

import com.kushnir.app.easytofind.data.service.ApiService
import com.kushnir.app.easytofind.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    factory { createWebService<ApiService>() }
}

fun getBaseOkHttpClient() = OkHttpClient()

/**
 * Base
 */

private val getHttpLoggingInterceptor = run {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.apply {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }
}

private fun getRequestInterceptor() =
    Interceptor { chain ->
        val token = "ce6b4e89-29ab-42ae-a685-35763e446115"

        val url = chain.request()
            .url
            .newBuilder()
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .addHeader("X-API-KEY", token)
            .build()

        return@Interceptor chain.proceed(request)
    }

fun createOkHttpClient(): OkHttpClient {
    return getBaseOkHttpClient()
        .newBuilder()
        .addNetworkInterceptor(getRequestInterceptor())
        .addInterceptor(getHttpLoggingInterceptor)
        .build()
}

fun getMoshiInstance(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

fun getRetrofit(okHttpClient: OkHttpClient): Retrofit? {

    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(getMoshiInstance()))
        .build()
}

inline fun <reified T> createWebService(): T? =
    getRetrofit(createOkHttpClient())?.create(T::class.java)