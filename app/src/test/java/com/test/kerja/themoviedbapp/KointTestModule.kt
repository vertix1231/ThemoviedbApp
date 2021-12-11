package com.test.kerja.themoviedbapp

import android.app.Application
import com.test.kerja.themoviedbapp.data.DataRepository
import com.test.kerja.themoviedbapp.db.ShowtaimentDatabase
import com.test.kerja.themoviedbapp.network.RetroBuilder
import com.test.kerja.themoviedbapp.ui.detail.DetailViewModel
import com.test.kerja.themoviedbapp.ui.listui.ListHomeViewModel
import com.test.kerja.themoviedbapp.utils.EspressoIdlingResource
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.mockito.Mockito.mock

object KointTestModule {
    private val mockedAndroidContext = mock(Application::class.java)

    val appModuleTest = module {

        single { DataRepository(get(), get()) }
        single { RetroBuilder.tmApi }
        single { EspressoIdlingResource() }
        single { ShowtaimentDatabase.getDatabase(mockedAndroidContext).showtaimentDao() }
        viewModel { ListHomeViewModel(get(), get()) }
        viewModel { DetailViewModel(get(), get()) }
    }
}