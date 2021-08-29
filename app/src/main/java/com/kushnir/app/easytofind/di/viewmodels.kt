package com.kushnir.app.easytofind.di

import com.kushnir.app.easytofind.ui.main.details.FilmDetailsViewModel
import com.kushnir.app.easytofind.ui.main.liked.LikedFilmsViewModel
import com.kushnir.app.easytofind.ui.main.list_fragment.FilmsListViewModel
import com.kushnir.app.easytofind.ui.main.top.TopFilmsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel { TopFilmsViewModel(get()) }

    viewModel { FilmsListViewModel(get(), get()) }
    
    viewModel { FilmDetailsViewModel(get()) }

    viewModel { LikedFilmsViewModel(get()) }

}