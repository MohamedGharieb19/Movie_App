package com.gharieb.movie_app.adapters.homeAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gharieb.movie_app.data.trendingTv.Tv
import com.gharieb.movie_app.databinding.TrendingTvItemBinding

class TrendingTvAdapter(): RecyclerView.Adapter<TrendingTvAdapter.viewHolder>() {


    class viewHolder(val binding: TrendingTvItemBinding): RecyclerView.ViewHolder(binding.root) {}

    private val diffUtil = object : DiffUtil.ItemCallback<Tv>(){
        override fun areItemsTheSame(oldItem: Tv, newItem: Tv): Boolean {
            return oldItem.id== newItem.id
        }
        override fun areContentsTheSame(oldItem: Tv, newItem: Tv): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(TrendingTvItemBinding.inflate(
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
    }



}