package com.test.kerja.themoviedbapp.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.test.kerja.themoviedbapp.ui.favorite.favoritefragment.MovieFavoriteFragment
import com.test.kerja.themoviedbapp.ui.favorite.favoritefragment.TvshowFavoriteFragment


class SectionsFavoriteAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = MovieFavoriteFragment()
            1 -> fragment = TvshowFavoriteFragment()
        }
        return fragment as Fragment
    }

}