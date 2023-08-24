package com.gharieb.movie_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gharieb.movie_app.R
import com.gharieb.movie_app.adapters.CategoriesAdapter
import com.gharieb.movie_app.adapters.homeAdapters.TrendingMovieAdapter
import com.gharieb.movie_app.adapters.homeAdapters.TrendingPeopleAdapter
import com.gharieb.movie_app.adapters.homeAdapters.TrendingTvAdapter
import com.gharieb.movie_app.databinding.FragmentHomePageBinding
import com.gharieb.movie_app.databinding.FragmentMyListBinding
import com.gharieb.movie_app.viewModels.HomeViewModel
import com.gharieb.movie_app.viewModels.MyListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyListFragment : Fragment() {
    private lateinit var binding: FragmentMyListBinding
    private val viewModel: MyListViewModel by viewModels()
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var moviesByCategoryAdapter: TrendingMovieAdapter
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
    }

    private fun setupCategoriesAdapter(){
        categoriesAdapter = CategoriesAdapter()
        binding.categoriesRecyclerView.apply {
            adapter = categoriesAdapter
        }
    }

    private fun setupMoviesByCategoryAdapter(){
        moviesByCategoryAdapter = TrendingMovieAdapter()
        binding.moviesWithGenresRecyclerView.apply {
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

}