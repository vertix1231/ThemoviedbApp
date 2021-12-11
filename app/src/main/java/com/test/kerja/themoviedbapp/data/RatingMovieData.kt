package com.test.kerja.themoviedbapp.data


import com.google.gson.annotations.SerializedName

data class RatingMovieData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<Result>
) {
    data class Result(
        @SerializedName("iso_3166_1")
        val iso31661: String,
        @SerializedName("release_dates")
        val releaseDates: List<ReleaseDate>
    ) {
        data class ReleaseDate(
            @SerializedName("certification")
            val certification: String,
            @SerializedName("iso_639_1")
            val iso6391: Any,
            @SerializedName("note")
            val note: String,
            @SerializedName("release_date")
            val releaseDate: String,
            @SerializedName("type")
            val type: Int
        )
    }
}