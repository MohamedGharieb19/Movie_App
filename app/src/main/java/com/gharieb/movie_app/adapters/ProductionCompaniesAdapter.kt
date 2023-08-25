package com.gharieb.movie_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gharieb.movie_app.R
import com.gharieb.movie_app.data.getMovieById.ProductionCompany
import com.gharieb.movie_app.data.trendingPeople.People
import com.gharieb.movie_app.databinding.ProductionCompaniesItemBinding


class ProductionCompaniesAdapter(): RecyclerView.Adapter<ProductionCompaniesAdapter.viewHolder>() {

    class viewHolder(val binding: ProductionCompaniesItemBinding): RecyclerView.ViewHolder(binding.root) {}

    private val diffUtil = object : DiffUtil.ItemCallback<ProductionCompany>(){
        override fun areItemsTheSame(oldItem: ProductionCompany, newItem: ProductionCompany): Boolean {
            return oldItem.id== newItem.id
        }
        override fun areContentsTheSame(oldItem: ProductionCompany, newItem: ProductionCompany): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            ProductionCompaniesItemBinding.inflate(
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
        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w154" + data.logo_path)
            .placeholder(R.drawable.ic_person)
            .into(holder.binding.image)

    }

}