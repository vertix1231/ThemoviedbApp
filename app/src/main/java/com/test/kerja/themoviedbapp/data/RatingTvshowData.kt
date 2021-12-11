package com.test.kerja.themoviedbapp.data


import com.google.gson.annotations.SerializedName

data class RatingTvshowData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<Result>
) {
    data class Result(
        @SerializedName("iso_3166_1")
        val iso31661: String,
        @SerializedName("rating")
        val rating: String
    )
}