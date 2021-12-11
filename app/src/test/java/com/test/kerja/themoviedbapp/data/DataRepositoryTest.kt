package com.test.kerja.themoviedbapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import com.nhaarman.mockitokotlin2.verify
import com.test.kerja.themoviedbapp.KointTestModule.appModuleTest
import com.test.kerja.themoviedbapp.db.ShowtaimentDao
import com.test.kerja.themoviedbapp.db.ShowtaimentEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.test.KoinTest
import org.koin.test.get
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class DataRepositoryTest : KoinTest{
    private lateinit var repository: DataRepository
    private val dispatcher = TestCoroutineDispatcher()




    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dao = Mockito.mock(ShowtaimentDao::class.java)

    @Suppress("DEPRECATION")
    @Before
    fun setUp() {
        startKoin {
            androidLogger(Level.NONE)
            androidLogger()
//            androidContext(androidContext = InstrumentationRegistry.getContext() )
            modules(appModuleTest)
        }
        Dispatchers.setMain(dispatcher)
        repository = DataRepository(get(), dao)
        MockitoAnnotations.initMocks(this)
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        GlobalContext.stopKoin()
    }



    @Test
    fun allLikedArts() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, ShowtaimentEntity>
        Mockito.`when`(dao.getFavoriteList("tv")).thenReturn(dataSourceFactory)


    }

    @Test
    fun getFilms() {
        runBlocking {
            val films = repository.getFilms()
            Assert.assertNotNull(films)
            val data = films.results[0].originalTitle
            println(data)
        }
    }

    @Test
    fun getTvs() {
        runBlocking {
            val tvs = repository.getTvs()
            Assert.assertNotNull(tvs)
            val data = tvs.results[0].originalName
            println(data)
        }
    }

    @Test
    fun getFilmDetail() {
        runBlocking {
            val films = repository.getFilms()
            Assert.assertNotNull(films)
            val data = films.results[0]
            val filmId = data.id
            val filmDetail = repository.getFilmDetail(filmId)
            Assert.assertEquals(data.originalTitle, filmDetail.originalTitle)
        }
    }

    @Test
    fun getTvDetail() {
        runBlocking {
            val tvs = repository.getTvs()
            Assert.assertNotNull(tvs)
            val data = tvs.results[0]
            val tvId = data.id
            val tvDetail = repository.getTvDetail(tvId)
            Assert.assertEquals(data.originalName, tvDetail.originalName)
        }
    }

    @Test
    fun getFilmRating() {
        runBlocking {
            val films = repository.getFilms()
            Assert.assertNotNull(films)
            val data = films.results[0]
            val filmId = data.id
            val rating = repository.getFilmRating(filmId)
            Assert.assertNotNull(rating)
            Assert.assertEquals(data.id, rating.id)
        }
    }

    @Test
    fun getTvRating() {
        runBlocking {
            val tvs = repository.getTvs()
            Assert.assertNotNull(tvs)
            val data = tvs.results[0]
            val tvId = data.id
            val rating = repository.getTvRating(tvId)
            Assert.assertNotNull(rating)
            Assert.assertEquals(rating.id, data.id)
        }
    }

    @Test
    fun testInsert() {
        val artEntity =
            ShowtaimentEntity(id = 1, title = "raisa", photo = "tasya", type = "tv", year = "2020")
        runBlocking {
            repository.insert(artEntity)
            verify(dao).insert(artEntity)
        }
    }

    @Test
    fun testDelete() {
        val artEntity =
            ShowtaimentEntity(id = 1, title = "raisa", photo = "tasya", type = "tv", year = "2020")
        runBlocking {
            repository.delete(artEntity)
            verify(dao).delete(artEntity)
        }
    }
}