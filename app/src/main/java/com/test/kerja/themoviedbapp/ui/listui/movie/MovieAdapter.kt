package com.test.kerja.themoviedbapp.ui.listui.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.kerja.themoviedbapp.data.MovieResultResponses
import com.test.kerja.themoviedbapp.databinding.ItemRowFilm2Binding
import com.test.kerja.themoviedbapp.ui.detail.DetailActivity


class MovieAdapter : RecyclerView.Adapter<MovieAdapter.FilmViewHolder>() {
    private val listFilms = ArrayList<MovieResultResponses>()

    fun setData(listFilms: List<MovieResultResponses>) {
        this.listFilms.clear()
        this.listFilms.addAll(listFilms)
        notifyDataSetChanged()
    }

    class FilmViewHolder(private val binding: ItemRowFilm2Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(film: MovieResultResponses) {
            with(binding) {
                tvYear.text = film.releaseDate.subSequence(0, 4)
                tvDataTitle.text = film.title
                Glide
                    .with(imgView.context)
                    .load("https://image.tmdb.org/t/p/original/${film.posterPath}")
                    .into(imgView)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.TYPE_TAG, DetailActivity.ID_FILM)
                    intent.putExtra(DetailActivity.ID_TAG, film.id)
                    itemView.context.startActivity(intent)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val binding = ItemRowFilm2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = listFilms[position]
        holder.bind(film)
    }

    override fun getItemCount(): Int {
        return listFilms.count()
    }
}