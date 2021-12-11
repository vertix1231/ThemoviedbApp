package com.test.kerja.themoviedbapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = ShowtaimentDao.TABLE_NAME)
data class ShowtaimentEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val photo: String,
    val year: String,
    val type: String
)