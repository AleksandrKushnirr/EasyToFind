package com.kushnir.app.easytofind.di

import com.kushnir.app.easytofind.domain.FilmsInteractor
import org.koin.dsl.module

val useCasesModule = module {

    factory { FilmsInteractor(get()) }

}