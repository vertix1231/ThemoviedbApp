package com.test.kerja.themoviedbapp.data


import com.google.gson.annotations.SerializedName

data class TvShowHead(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<TvshowResultResponses>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)