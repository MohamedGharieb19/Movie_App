package com.gharieb.movie_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gharieb.movie_app.R
import com.gharieb.movie_app.data.categories.Categories
import com.gharieb.movie_app.databinding.TabsItemBinding

class CategoriesAdapter: RecyclerView.Adapter<CategoriesAdapter.viewHolder>() {

    lateinit var onCategoryClick: ((Categories)  -> Unit )

    private var selectedItemPosition: Int = RecyclerView.NO_POSITION

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

        val colorResId = R.color.green
        val colorStateList = ContextCompat.getColorStateList(holder.itemView.context, colorResId)

        val adapterPosition = holder.adapterPosition
        if (selectedItemPosition == adapterPosition) {
            holder.binding.layout.backgroundTintList = colorStateList
        } else {
            holder.binding.layout.backgroundTintList = null
        }


        holder.itemView.setOnClickListener {
            val clickedPosition = holder.adapterPosition
            if (selectedItemPosition != clickedPosition) {
                val previousSelectedPosition = selectedItemPosition
                selectedItemPosition = clickedPosition
                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(selectedItemPosition)
            }
            onCategoryClick.invoke(data)
            holder.binding.layout.backgroundTintList = colorStateList
        }
    }
    fun setInitialSelectedItem(position: Int) {
        val previousSelectedPosition = selectedItemPosition
        selectedItemPosition = position

        // Update the background color of the previously selected item to default
        if (previousSelectedPosition != RecyclerView.NO_POSITION) {
            notifyItemChanged(previousSelectedPosition)
        }

        // Update the background color of the newly selected item
        notifyItemChanged(selectedItemPosition)
    }

}