package com.test.kerja.themoviedbapp.ui.listui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.kerja.themoviedbapp.data.MovieHead
import com.test.kerja.themoviedbapp.data.MovieResultResponses
import com.test.kerja.themoviedbapp.databinding.FragmentMovieBinding
import com.test.kerja.themoviedbapp.ui.listui.ListHomeViewModel
import com.test.kerja.themoviedbapp.utils.Status
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding
    private val homeViewModel: ListHomeViewModel by sharedViewModel()
    //    private lateinit var adapter : MovieAdapter
    var adapter = MovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {


            with(binding.recView) {

//                layoutManager = GridLayoutManager(context, 2)
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(false)
                this.adapter = adapter
            }
        }
        setObservers()
    }

    private fun setObservers() {
        homeViewModel.getFilmku()
        homeViewModel.films.observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        // recyclerView.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let { results ->
                            results as MovieHead
                            updateData(results.results)
                        }
                    }
                    Status.ERROR -> {
                        // recyclerView.visibility = View.VISIBLE
                        // progressBar.visibility = View.GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        //  progressBar.visibility = View.VISIBLE
                        // recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun updateData(movieResultResponses: List<MovieResultResponses>) {
        binding.recView.adapter = adapter
        adapter.setData(movieResultResponses)
    }
}