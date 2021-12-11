package com.test.kerja.themoviedbapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [ShowtaimentEntity::class], version = 3, exportSchema = false)
abstract class ShowtaimentDatabase : RoomDatabase() {
    abstract fun showtaimentDao(): ShowtaimentDao

    companion object {
        @Volatile
        private var INSTANCE: ShowtaimentDatabase? = null

        fun getDatabase(context: Context): ShowtaimentDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    ShowtaimentDatabase::class.java,
                    "showtaimentvertix_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}