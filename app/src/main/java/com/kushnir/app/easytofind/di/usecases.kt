package com.kushnir.app.easytofind.di

import com.kushnir.app.easytofind.domain.FilmDetailsInteractor
import com.kushnir.app.easytofind.domain.LikedFilmsInteractor
import com.kushnir.app.easytofind.domain.TopFilmsInteractor
import org.koin.dsl.module

val useCasesModule = module {

    factory { TopFilmsInteractor(get(), get()) }

    factory { FilmDetailsInteractor(get()) }

    factory { LikedFilmsInteractor(get()) }

}