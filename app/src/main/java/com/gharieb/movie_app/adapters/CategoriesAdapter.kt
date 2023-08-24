package com.gharieb.movie_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gharieb.movie_app.data.categories.Categories
import com.gharieb.movie_app.databinding.TabsItemBinding


class CategoriesAdapter: RecyclerView.Adapter<CategoriesAdapter.viewHolder>() {

    lateinit var onCategoryClick: ((Categories)  -> Unit )

    class viewHolder(val binding: TabsItemBinding): RecyclerView.ViewHolder(binding.root) {}

    private val diffUtil = object : DiffUtil.ItemCallback<Categories>(){
        override fun areItemsTheSame(oldItem: Categories, newItem: Categories): Boolean {
            return oldItem.id== newItem.id
        }
        override fun areContentsTheSame(oldItem: Categories, newItem: Categories): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            TabsItemBinding.inflate(
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
        holder.binding.categoryTextView.text = data.name

        holder.itemView.setOnClickListener {
            onCategoryClick.invoke(data)
        }
    }

}