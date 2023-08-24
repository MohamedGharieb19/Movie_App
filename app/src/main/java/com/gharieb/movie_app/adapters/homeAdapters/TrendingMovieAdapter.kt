package com.gharieb.movie_app.adapters.homeAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gharieb.movie_app.data.categories.Categories
import com.gharieb.movie_app.data.trendingMovies.Movie
import com.gharieb.movie_app.databinding.TrendingMoviesItemBinding

class TrendingMovieAdapter(): RecyclerView.Adapter<TrendingMovieAdapter.viewHolder>() {
    lateinit var onMovieClick: ((Movie)  -> Unit )
    class viewHolder(val binding: TrendingMoviesItemBinding): RecyclerView.ViewHolder(binding.root) {}

    private val diffUtil = object : DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id== newItem.id
        }
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            TrendingMoviesItemBinding.inflate(
            LayoutInflater.from(parent.context)
            ,parent
            ,false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val data = differ.currentList[position]
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w154" + data.image)
            .into(holder.binding.imageView)
        holder.binding.rateTextView.text = data.vote_average.toString()

        holder.itemView.setOnClickListener {
            onMovieClick.invoke(data)
        }
    }

}