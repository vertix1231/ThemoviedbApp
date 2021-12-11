package com.test.kerja.themoviedbapp.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.test.kerja.themoviedbapp.db.ShowtaimentDao
import com.test.kerja.themoviedbapp.db.ShowtaimentEntity
import com.test.kerja.themoviedbapp.network.TmApi


class DataRepository(private val tmApi: TmApi, private val showtaimentDao: ShowtaimentDao) {
    suspend fun getFilms() = tmApi.getMovies()
    suspend fun getTvs() = tmApi.getTvs()
    suspend fun getFilmDetail(movieID: Int) = tmApi.getFilmDetail(movieID)
    suspend fun getTvDetail(tvID: Int) = tmApi.getTvDetail(tvID)
    suspend fun getFilmRating(movieID: Int) = tmApi.getFilmRating(movieID)
    suspend fun getTvRating(tvID: Int) = tmApi.getTvRating(tvID)
    @Suppress("DEPRECATION")
    fun allLikedAShowtaiment(type: String): LiveData<PagedList<ShowtaimentEntity>> =
        LivePagedListBuilder(showtaimentDao.getFavoriteList(type), 20).build()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(showtaimentEntity: ShowtaimentEntity) {
        showtaimentDao.insert(showtaimentEntity)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(showtaimentEntity: ShowtaimentEntity) {
        showtaimentDao.delete(showtaimentEntity)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun searchArt(id: Int): Int {
        return showtaimentDao.searchArt(id)
    }
}