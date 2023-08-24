package com.gharieb.movie_app.adapters.homeAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gharieb.movie_app.data.trendingPeople.People
import com.gharieb.movie_app.databinding.TopPeopleItemBinding


class TrendingPeopleAdapter(): RecyclerView.Adapter<TrendingPeopleAdapter.viewHolder>() {

    class viewHolder(val binding: TopPeopleItemBinding): RecyclerView.ViewHolder(binding.root) {}

    private val diffUtil = object : DiffUtil.ItemCallback<People>(){
        override fun areItemsTheSame(oldItem: People, newItem: People): Boolean {
            return oldItem.id== newItem.id
        }
        override fun areContentsTheSame(oldItem: People, newItem: People): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            TopPeopleItemBinding.inflate(
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
            .into(holder.binding.image)
        holder.binding.name.text = data.name
    }

}