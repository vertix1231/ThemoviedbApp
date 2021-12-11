package com.test.kerja.themoviedbapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.test.kerja.themoviedbapp.data.DataRepository
import com.test.kerja.themoviedbapp.data.RatingMovieData
import com.test.kerja.themoviedbapp.data.RatingTvshowData
import com.test.kerja.themoviedbapp.db.ShowtaimentEntity
import com.test.kerja.themoviedbapp.utils.EspressoIdlingResource
import com.test.kerja.themoviedbapp.utils.Resource
import kotlinx.coroutines.launch


@Suppress("DEPRECATION")
class DetailViewModel(val repository: DataRepository, val espresso: EspressoIdlingResource) : ViewModel() {
    val selectedFilm = MutableLiveData<Resource<Any>>()
    val selectedTv = MutableLiveData<Resource<Any>>()
    fun allLikedArts(type: String): LiveData<PagedList<ShowtaimentEntity>> = repository.allLikedAShowtaiment(type)

    suspend fun isLiked(id: Int): Boolean {
        val count: Int = repository.searchArt(id)
        return count > 0
    }

    fun setFilm(movieID: Int) {
        espresso.increment()
        viewModelScope.launch {
            try {
                selectedFilm.postValue(Resource.success(data = repository.getFilmDetail(movieID)))
            } catch (exception: Exception) {
                selectedFilm.postValue(
                    Resource.error(
                        data = null,
                        message = exception.message ?: "Error Occurred!"
                    )
                )
            }
            espresso.decrement()

        }
    }

    fun setTv(tvID: Int) {
        espresso.increment()
        viewModelScope.launch {
            try {
                selectedTv.postValue(Resource.success(data = repository.getTvDetail(tvID)))
            } catch (exception: Exception) {
                selectedTv.postValue(
                    Resource.error(
                        data = null,
                        message = exception.message ?: "Error Occurred!"
                    )
                )
            }
            espresso.decrement()

        }
    }

    suspend fun getFilmRating(movieID: Int): String {
        espresso.increment()
        lateinit var response: RatingMovieData
        var rating = "N/A"
        try {
            response = repository.getFilmRating(movieID)
            for (resp in response.results) {
                if (resp.iso31661 == "US") {
                    rating = resp.releaseDates[0].certification
                }
            }
        } catch (exception: Exception) {
        }
        espresso.decrement()
        return rating
    }

    suspend fun getTvRating(tvID: Int): String {
        espresso.increment()
        lateinit var response: RatingTvshowData
        var rating = "N/A"
        try {
            response = repository.getTvRating(tvID)
            rating = response.results[0].rating
        } catch (exception: Exception) {
        }
        espresso.decrement()
        return rating

    }

    fun insert(showtaimentEntity: ShowtaimentEntity) {
        espresso.increment()
        viewModelScope.launch {
            repository.insert(showtaimentEntity)
            espresso.decrement()
        }
    }

    fun delete(showtaimentEntity: ShowtaimentEntity) {
        espresso.increment()
        viewModelScope.launch {
            repository.delete(showtaimentEntity)
            espresso.decrement()
        }
    }
}