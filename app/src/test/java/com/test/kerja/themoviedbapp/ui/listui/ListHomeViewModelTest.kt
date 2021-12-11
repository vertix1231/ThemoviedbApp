package com.test.kerja.themoviedbapp.ui.listui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer

import com.nhaarman.mockitokotlin2.verify
import com.test.kerja.themoviedbapp.KointTestModule.appModuleTest
import com.test.kerja.themoviedbapp.data.DataRepository
import com.test.kerja.themoviedbapp.data.MovieHead
import com.test.kerja.themoviedbapp.data.TvShowHead
import com.test.kerja.themoviedbapp.utils.EspressoIdlingResource
import com.test.kerja.themoviedbapp.utils.LiveDataTestUtil
import com.test.kerja.themoviedbapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class ListHomeViewModelTest : KoinTest {

//    private val repoReal by inject<DataRepository>()
//    private val dispatcher = TestCoroutineDispatcher()
    private val repoReal by inject<DataRepository>()
    private val dispatcher = TestCoroutineDispatcher()


//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

//    @Mock
//    private lateinit var observer: Observer<Resource<Any>>

    @Mock
    private lateinit var observer : Observer<Resource<Any>>

//    @Mock
//    private lateinit var observer2: Observer<Resource<Any>>
    @Mock
    private lateinit var observer2: Observer<Resource<Any>>

//    private lateinit var listViewModel: ListHomeViewModel
    private lateinit var listViewModel: ListHomeViewModel

//    @Mock
//    private lateinit var espresso: EspressoIdlingResource
    @Mock
    private lateinit var espresso: EspressoIdlingResource

    //    @Suppress("DEPRECATION")
//    @Before
//    fun setUp() {
//        startKoin {
//            androidLogger(Level.NONE)
//            androidLogger()
////            androidContext()
//            modules(appModule)
//        }
//        Dispatchers.setMain(dispatcher)
//        MockitoAnnotations.initMocks(this)
//        listViewModel = ListHomeViewModel(repoReal, espresso)
//    }
    @Suppress("DEPRECATION")
    @Before
    fun setUp(){
        startKoin {
            androidLogger(Level.NONE)
            androidLogger()
//            androidContext()
            modules(appModuleTest)
        }
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.initMocks(this)
        listViewModel = ListHomeViewModel(repoReal,espresso)
    }

//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//        stopKoin()
//    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        stopKoin()
    }

    @Test
    fun getFilm() {


        val films: MovieHead = runBlocking { repoReal.getFilms() }
        val resource = Resource.success(data = films)
        listViewModel.getFilmku()
        val value = LiveDataTestUtil.getValue(listViewModel.films)
        assertNotNull(value)
        val data = value.data as MovieHead
        println(value)
        listViewModel.films.observeForever(observer)
        verify(observer).onChanged(resource)
        assertEquals(data.totalResults,films.totalResults)

    }

    @Test
    fun getTv() {
        val tvs: TvShowHead = runBlocking { repoReal.getTvs() }
        Assert.assertNotNull(tvs)

        val resources = Resource.success(data = tvs)
        listViewModel.getTvku()
        val value = LiveDataTestUtil.getValue(listViewModel.tvs)
        Assert.assertNotNull(value)
        val data = value.data as TvShowHead
        listViewModel.tvs.observeForever(observer2)
        verify(observer2).onChanged(resources)
        assertEquals(data.totalResults, tvs.totalResults)

    }
}