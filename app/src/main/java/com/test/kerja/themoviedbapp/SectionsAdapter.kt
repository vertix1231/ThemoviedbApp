package com.test.kerja.themoviedbapp

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.test.kerja.themoviedbapp.ui.listui.movie.MovieFragment
import com.test.kerja.themoviedbapp.ui.listui.tv.TvshowFragment


class SectionsAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = MovieFragment()
            1 -> fragment = TvshowFragment()
        }
        return fragment as Fragment
    }

}
