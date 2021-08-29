package com.kushnir.app.easytofind.di

import com.kushnir.app.easytofind.data.repositories.FilmsDBRepository
import com.kushnir.app.easytofind.data.repositories.FilmsRepository
import org.koin.dsl.module

val repositoriesModule = module {

    factory { FilmsRepository(get()) }

    factory { FilmsDBRepository(get()) }
}