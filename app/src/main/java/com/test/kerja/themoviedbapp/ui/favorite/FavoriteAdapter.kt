package com.test.kerja.themoviedbapp.ui.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.kerja.themoviedbapp.databinding.ItemRowFilm2Binding
import com.test.kerja.themoviedbapp.db.ShowtaimentEntity
import com.test.kerja.themoviedbapp.ui.detail.DetailActivity


class FavoriteAdapter : PagedListAdapter<ShowtaimentEntity, FavoriteAdapter.FavoriteViewHolder>(
    DIFF_CALLBACK
) {
    private val list = ArrayList<ShowtaimentEntity>()

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<ShowtaimentEntity> =
            object : DiffUtil.ItemCallback<ShowtaimentEntity>() {
                override fun areItemsTheSame(oldShowtaiment: ShowtaimentEntity, newShowtaiment: ShowtaimentEntity): Boolean {
                    return oldShowtaiment.id == newShowtaiment.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldShowtaiment: ShowtaimentEntity, newShowtaiment: ShowtaimentEntity): Boolean {
                    return oldShowtaiment == newShowtaiment
                }
            }
    }

    fun setData(list: List<ShowtaimentEntity>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class FavoriteViewHolder(private val binding: ItemRowFilm2Binding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShowtaimentEntity) {
            with(binding) {
                tvYear.text = item.year
                tvDataTitle.text = item.title
                Glide
                    .with(imgView.context)
                    .load(item.photo)
                    .into(imgView)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    if (item.type == "tv") {
                        intent.putExtra(DetailActivity.TYPE_TAG, DetailActivity.ID_TV)
                    } else {
                        intent.putExtra(DetailActivity.TYPE_TAG, DetailActivity.ID_FILM)
                    }
                    intent.putExtra(DetailActivity.ID_TAG, item.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder {
        val binding =
            ItemRowFilm2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val datalist = list[position]
        holder.bind(datalist)
    }

    override fun getItemCount(): Int {
        return list.count()
    }
}