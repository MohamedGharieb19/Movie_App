package com.gharieb.movie_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gharieb.movie_app.R
import com.gharieb.movie_app.adapters.CategoriesAdapter
import com.gharieb.movie_app.adapters.SearchAdapter
import com.gharieb.movie_app.databinding.FragmentMyListBinding
import com.gharieb.movie_app.viewModels.MyListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyListFragment : Fragment() {
    private lateinit var binding: FragmentMyListBinding
    private val viewModel: MyListViewModel by viewModels()
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var moviesByCategoryAdapter: SearchAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMyListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCategories()
        getMoviesByCategory()
        onMovieClick()
        onOpenFragment()

        binding.searchIcon.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }
    }

    private fun setupCategoriesAdapter(){
        categoriesAdapter = CategoriesAdapter()
        binding.categoriesRecyclerView.apply {
            adapter = categoriesAdapter
        }
    }

    private fun setupMoviesByCategoryAdapter(){
        moviesByCategoryAdapter = SearchAdapter()
        binding.moviesWithGenresRecyclerView.apply {
            layoutManager = GridLayoutManager(context,2, RecyclerView.VERTICAL,false)
            adapter = moviesByCategoryAdapter
        }
    }

    private fun getCategories(){
        setupCategoriesAdapter()
        viewModel.getCategoriesOfMovies()
        lifecycleScope.launch {
            viewModel.categoriesList.collect{ categoriesAdapter.differ.submitList(it) }
        }
    }

    private fun getMoviesByCategory(){
        setupMoviesByCategoryAdapter()
        categoriesAdapter.onCategoryClick = {
            viewModel.getMoviesByCategory(it.id)
            lifecycleScope.launch {
                viewModel.movieByCategoryList.collect{ moviesByCategoryAdapter.differ.submitList(it) }
            }
        }
    }

    private fun onMovieClick(){
        moviesByCategoryAdapter.onMovieClick = { data ->
            val fragment = MovieDetailsFragment()
            val bundle = Bundle()
            bundle.putInt("movie_Id",data.id)
            fragment.arguments = bundle
            findNavController().navigate(R.id.movieDetailsFragment,bundle)
        }
    }

    private fun onOpenFragment(){
        categoriesAdapter.setInitialSelectedItem(0)
        viewModel.getMoviesByCategory(28)
        lifecycleScope.launch {
            viewModel.movieByCategoryList.collect{ moviesByCategoryAdapter.differ.submitList(it) }
        }
    }



}