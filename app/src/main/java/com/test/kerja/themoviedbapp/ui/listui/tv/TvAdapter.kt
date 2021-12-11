package com.test.kerja.themoviedbapp.ui.listui.tv

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.kerja.themoviedbapp.data.TvshowResultResponses
import com.test.kerja.themoviedbapp.databinding.ItemRowFilm2Binding
import com.test.kerja.themoviedbapp.ui.detail.DetailActivity


class TvAdapter : RecyclerView.Adapter<TvAdapter.TvViewHolder>() {
    private val listFilms = ArrayList<TvshowResultResponses>()

    fun setData(listFilms: List<TvshowResultResponses>) {
        this.listFilms.clear()
        this.listFilms.addAll(listFilms)
        notifyDataSetChanged()
    }

    class TvViewHolder(private val binding: ItemRowFilm2Binding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: TvshowResultResponses) {
            with(binding) {
                tvYear.text = tv.firstAirDate.subSequence(0, 4)
                tvDataTitle.text = tv.name
                Glide
                    .with(imgView.context)
                    .load("https://image.tmdb.org/t/p/original/${tv.posterPath}")
                    .into(imgView)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.TYPE_TAG, DetailActivity.ID_TV)
                    intent.putExtra(DetailActivity.ID_TAG, tv.id)
                    itemView.context.startActivity(intent)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val binding = ItemRowFilm2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val film = listFilms[position]
        holder.bind(film)
    }

    override fun getItemCount(): Int {
        return listFilms.count()
    }
}