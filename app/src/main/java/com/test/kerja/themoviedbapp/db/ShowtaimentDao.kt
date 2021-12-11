package com.test.kerja.themoviedbapp.db

import androidx.paging.DataSource
import androidx.room.*


@Dao
interface ShowtaimentDao {
    companion object {
        const val TABLE_NAME = "showtaiment_table"
    }

    @Query("SELECT * FROM $TABLE_NAME WHERE type =:type")
    fun getFavoriteList(type: String): DataSource.Factory<Int, ShowtaimentEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(showtaimentEntity: ShowtaimentEntity)

    @Delete
    suspend fun delete(showtaimentEntity: ShowtaimentEntity)

    @Query("SELECT COUNT(id) FROM $TABLE_NAME WHERE id =:id")
    suspend fun searchArt(id: Int): Int
}