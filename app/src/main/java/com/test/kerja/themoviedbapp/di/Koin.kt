package com.test.kerja.themoviedbapp.di

import com.test.kerja.themoviedbapp.data.DataRepository
import com.test.kerja.themoviedbapp.db.ShowtaimentDatabase
import com.test.kerja.themoviedbapp.network.RetroBuilder
import com.test.kerja.themoviedbapp.ui.detail.DetailViewModel
import com.test.kerja.themoviedbapp.ui.listui.ListHomeViewModel
import com.test.kerja.themoviedbapp.utils.EspressoIdlingResource
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


object Koin {
    val appModule = module {

        single { DataRepository(get(), get()) }
        single { RetroBuilder.tmApi }
        single { EspressoIdlingResource() }
        single { ShowtaimentDatabase.getDatabase(get()).showtaimentDao() }
        viewModel { ListHomeViewModel(get(), get()) }
        viewModel { DetailViewModel(get(), get()) }
    }


}