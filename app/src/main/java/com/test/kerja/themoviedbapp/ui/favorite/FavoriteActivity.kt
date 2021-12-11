package com.test.kerja.themoviedbapp.ui.favorite

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.tabs.TabLayoutMediator
import com.test.kerja.themoviedbapp.R
import com.test.kerja.themoviedbapp.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.movie_tab,
            R.string.tv_tab
        )
    }
    private lateinit var binding: ActivityFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager = binding.viewPager
        val sectionsAdapter = SectionsFavoriteAdapter(this)
        viewPager.adapter = sectionsAdapter
        val tabs = binding.tabLayout
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.title = "Favorites"

    }
}