package com.test.kerja.themoviedbapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroBuilder {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val tmApi: TmApi = getRetrofit().create(TmApi::class.java)

}