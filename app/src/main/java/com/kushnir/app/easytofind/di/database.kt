package com.kushnir.app.easytofind.di

import androidx.room.Room
import com.kushnir.app.easytofind.data.room.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "app_database.db")
                .build()
    }
    single { get<AppDatabase>().filmsDao() }
}