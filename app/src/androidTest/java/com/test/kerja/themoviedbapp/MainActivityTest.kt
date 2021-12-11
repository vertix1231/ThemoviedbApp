package com.test.kerja.themoviedbapp

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.test.kerja.themoviedbapp.data.*
import com.test.kerja.themoviedbapp.utils.EspressoIdlingResource
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
class MainActivityTest : KoinTest{
    val repository by inject<DataRepository>()
    val idlingResource by inject<EspressoIdlingResource>()
    lateinit var films: MovieHead
    lateinit var tvs: TvShowHead
    lateinit var detailFilm: MovieDetailData
    lateinit var detailTv: TvDetailData
    lateinit var ratingFilm: RatingMovieData
    lateinit var ratingTv: RatingTvshowData

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        runBlocking {
            films = repository.getFilms()
            tvs = repository.getTvs()
            detailFilm = repository.getFilmDetail(films.results[0].id)
            detailTv = repository.getTvDetail(tvs.results[0].id)
            ratingFilm = repository.getFilmRating(films.results[0].id)
            ratingTv = repository.getTvRating(tvs.results[0].id)
            println(ratingTv)

        }
        IdlingRegistry.getInstance().register(idlingResource.idlingResource)
    }

    @Test
    fun loadAndDeleteFavorites() {
        Espresso.onView(withId(R.id.recView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        Espresso.onView(withId(R.id.floatingActionButton)).perform(ViewActions.click()) //tambah ke favorit
        Espresso.pressBack()
        Espresso.onView(withId(R.id.fav)).perform(ViewActions.click()) //klik menu fav
        Espresso.onView(withId(R.id.recView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        ) //klik entry pertama
        Espresso.onView(withId(R.id.tvTitle))
            .check(ViewAssertions.matches(withText(films.results[0].originalTitle)))
        var builder = StringBuilder()
        val iterator = detailFilm.genres.iterator()
        while (iterator.hasNext()) {
            val obj = iterator.next()
            if (iterator.hasNext()) {
                builder.append(obj.name)
                builder.append(", ")
            } else {
                builder.append(obj.name)
            }
        }
        Espresso.onView(withId(R.id.tvTitle))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvTitle))
            .check(ViewAssertions.matches(withText(films.results[0].originalTitle)))
        Espresso.onView(withId(R.id.tvOverview))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvOverview))
            .check(ViewAssertions.matches(withText(films.results[0].overview)))
        Espresso.onView(withId(R.id.imageView2))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvYear))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvYear)).check(
            ViewAssertions.matches(
                withText(
                    films.results[0].releaseDate.substring(
                        0,
                        4
                    )
                )
            )
        )
        Espresso.onView(withId(R.id.tvTags))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvTags))
            .check(ViewAssertions.matches(ViewMatchers.withText(builder.toString())))
        Espresso.onView(withId(R.id.tvLang))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvLang))
            .check(ViewAssertions.matches(ViewMatchers.withText(determineLang(detailFilm.originalLanguage))))
        Espresso.onView(withId(R.id.tvStatus))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvStatus))
            .check(ViewAssertions.matches(withText(detailFilm.status)))
        Espresso.onView(withId(R.id.tvAge))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvAge))
            .check(ViewAssertions.matches(ViewMatchers.withText(determineFilmRating())))
        Espresso.onView(withId(R.id.tvScore))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvScore)).check(
            ViewAssertions.matches(
                ViewMatchers.withText(
                    "${
                        (detailFilm.voteAverage * 10).toString().substring(0, 2)
                    }%"
                )
            )
        )
        Espresso.onView(withId(R.id.lang)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.stat)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.score))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.oView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.pressBack()
        Espresso.pressBack() //kembali ke main act
        Espresso.onView(ViewMatchers.withText("TV")).perform(ViewActions.click())
        Espresso.onView(withId(R.id.recView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.recView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        ) //klik tv pertama
        Espresso.onView(withId(R.id.floatingActionButton)).perform(ViewActions.click()) //tambah ke favorit
        Espresso.pressBack() //kembali ke main act
        Espresso.onView(withId(R.id.fav)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("TV")).perform(ViewActions.click())
        Espresso.onView(withId(R.id.recView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.recView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        builder = StringBuilder()
        val iterator2 = detailTv.genres.iterator()
        while (iterator2.hasNext()) {
            val obj = iterator2.next()
            if (iterator2.hasNext()) {
                builder.append(obj.name)
                builder.append(", ")
            } else {
                builder.append(obj.name)
            }
        }
        Espresso.onView(withId(R.id.tvTitle))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvTitle))
            .check(ViewAssertions.matches(withText(tvs.results[0].originalName)))
        Espresso.onView(withId(R.id.tvOverview))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvOverview))
            .check(ViewAssertions.matches(withText(tvs.results[0].overview)))
        Espresso.onView(withId(R.id.imageView2))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvYear))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvYear)).check(
            ViewAssertions.matches(
                withText(
                    tvs.results[0].firstAirDate.substring(
                        0,
                        4
                    )
                )
            )
        )
        Espresso.onView(withId(R.id.tvTags))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvTags))
            .check(ViewAssertions.matches(ViewMatchers.withText(builder.toString())))
        Espresso.onView(withId(R.id.tvLang))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvLang))
            .check(ViewAssertions.matches(ViewMatchers.withText(determineLang(detailTv.originalLanguage))))
        Espresso.onView(withId(R.id.tvStatus))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvStatus))
            .check(ViewAssertions.matches(withText(detailTv.status)))
        Espresso.onView(withId(R.id.tvAge))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvAge))
            .check(ViewAssertions.matches(ViewMatchers.withText(determineTvRating())))
        Espresso.onView(withId(R.id.tvScore))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvScore)).check(
            ViewAssertions.matches(
                ViewMatchers.withText(
                    "${
                        (detailTv.voteAverage * 10).toString().substring(0, 2)
                    }%"
                )
            )
        )
        Espresso.onView(withId(R.id.lang)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.stat)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.score))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.oView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.pressBack()
        Espresso.pressBack()
        //hapus movie dr favorit
        Espresso.onView(ViewMatchers.withText("MOVIES")).perform(ViewActions.click())
        Espresso.onView(withId(R.id.recView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        Espresso.onView(withId(R.id.floatingActionButton)).perform(ViewActions.click()) //hapus dr favorit
        Espresso.pressBack()
        //pindah ke tv
        Espresso.onView(ViewMatchers.withText("TV")).perform(ViewActions.click())
        Espresso.onView(withId(R.id.recView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        Espresso.onView(withId(R.id.floatingActionButton)).perform(ViewActions.click()) //hapus dr favorit
        Espresso.pressBack()

        Espresso.onView(withId(R.id.fav)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.recView))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
        Espresso.onView(ViewMatchers.withText("TV")).perform(ViewActions.click())
        Espresso.onView(withId(R.id.recView))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun  loadFilms() {

        Espresso.onView(withId(R.id.recView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.recView)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                films.totalResults
            )
        )
    }

    @Test
    fun loadTvs() {
        Espresso.onView(ViewMatchers.withText("TV")).perform(ViewActions.click())
        Espresso.onView(withId(R.id.recView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.recView)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                tvs.totalResults
            )
        )
    }

    @Test
    fun loadDetailFilm() {
        Espresso.onView(withId(R.id.recView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        val builder = StringBuilder()
        val iterator = detailFilm.genres.iterator()
        while (iterator.hasNext()) {
            val obj = iterator.next()
            if (iterator.hasNext()) {
                builder.append(obj.name)
                builder.append(", ")
            } else {
                builder.append(obj.name)
            }
        }
        Espresso.onView(withId(R.id.tvTitle))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvTitle))
            .check(ViewAssertions.matches(withText(films.results[0].originalTitle)))
        Espresso.onView(withId(R.id.tvOverview))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvOverview))
            .check(ViewAssertions.matches(withText(films.results[0].overview)))
        Espresso.onView(withId(R.id.imageView2))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvYear))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvYear)).check(
            ViewAssertions.matches(
                withText(
                    films.results[0].releaseDate.substring(
                        0,
                        4
                    )
                )
            )
        )
        Espresso.onView(withId(R.id.tvTags))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvTags))
            .check(ViewAssertions.matches(ViewMatchers.withText(builder.toString())))
        Espresso.onView(withId(R.id.tvLang))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvLang))
            .check(ViewAssertions.matches(ViewMatchers.withText(determineLang(detailFilm.originalLanguage))))
        Espresso.onView(withId(R.id.tvStatus))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvStatus))
            .check(ViewAssertions.matches(withText(detailFilm.status)))
        Espresso.onView(withId(R.id.tvAge))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvAge))
            .check(ViewAssertions.matches(ViewMatchers.withText(determineFilmRating())))
        Espresso.onView(withId(R.id.tvScore))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvScore)).check(
            ViewAssertions.matches(
                ViewMatchers.withText(
                    "${
                        (detailFilm.voteAverage * 10).toString().substring(0, 2)
                    }%"
                )
            )
        )
        Espresso.onView(withId(R.id.lang)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.stat)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.score))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.oView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    private fun determineLang(lang: String): String {
        return when (lang) {
            "en" -> "English"
            "es" -> "Espanol"
            "tr" -> "Turkish"
            "ja" -> "Japanese"
            "pl" -> "Polish"
            else -> lang
        }
    }

    @Test
    fun loadDetailTv() {
        Espresso.onView(ViewMatchers.withText("TV")).perform(ViewActions.click())
        Espresso.onView(withId(R.id.recView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        val builder = StringBuilder()
        val iterator = detailTv.genres.iterator()
        while (iterator.hasNext()) {
            val obj = iterator.next()
            if (iterator.hasNext()) {
                builder.append(obj.name)
                builder.append(", ")
            } else {
                builder.append(obj.name)
            }
        }
        Espresso.onView(withId(R.id.tvTitle))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvTitle))
            .check(ViewAssertions.matches(withText(tvs.results[0].originalName)))
        Espresso.onView(withId(R.id.tvOverview))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvOverview))
            .check(ViewAssertions.matches(withText(tvs.results[0].overview)))
        Espresso.onView(withId(R.id.imageView2))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvYear))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvYear)).check(
            ViewAssertions.matches(
                withText(
                    tvs.results[0].firstAirDate.substring(
                        0,
                        4
                    )
                )
            )
        )
        Espresso.onView(withId(R.id.tvTags))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvTags))
            .check(ViewAssertions.matches(ViewMatchers.withText(builder.toString())))
        Espresso.onView(withId(R.id.tvLang))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvLang))
            .check(ViewAssertions.matches(ViewMatchers.withText(determineLang(detailTv.originalLanguage))))
        Espresso.onView(withId(R.id.tvStatus))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvStatus))
            .check(ViewAssertions.matches(withText(detailTv.status)))
        Espresso.onView(withId(R.id.tvAge))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvAge))
            .check(ViewAssertions.matches(ViewMatchers.withText(determineTvRating())))
        Espresso.onView(withId(R.id.tvScore))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tvScore)).check(
            ViewAssertions.matches(
                ViewMatchers.withText(
                    "${
                        (detailTv.voteAverage * 10).toString().substring(0, 2)
                    }%"
                )
            )
        )
        Espresso.onView(withId(R.id.lang)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.stat)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.score))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.oView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    private fun determineFilmRating(): String {
        var rating = "N/A"
        val response = ratingFilm
        try {
            for (resp in response.results) {
                if (resp.iso31661 == "US") {
                    rating = resp.releaseDates[0].certification
                }
            }
        } catch (exception: Exception) {
        }
        return rating
    }

    private fun determineTvRating(): String {
        val response = ratingTv
        val rating = response.results[0].rating
        println(rating)
        return rating
    }
}