package com.gharieb.movie_app.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import com.gharieb.movie_app.R
import com.gharieb.movie_app.adapters.homeAdapters.TrendingMovieAdapter
import com.gharieb.movie_app.adapters.homeAdapters.TrendingPeopleAdapter
import com.gharieb.movie_app.adapters.homeAdapters.TrendingTvAdapter
import com.gharieb.movie_app.databinding.FragmentHomePageBinding
import com.gharieb.movie_app.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomePageFragment : Fragment() {
    private lateinit var binding: FragmentHomePageBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var tvAdapter: TrendingTvAdapter
    private lateinit var movieAdapter: TrendingMovieAdapter
    private lateinit var peopleAdapter: TrendingPeopleAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomePageBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTrendingTv()
        getTrendingMovies()
        getTrendingPeople()
        onMovieClick()
    }

    private fun setupTvAdapter(){
        tvAdapter = TrendingTvAdapter()
        binding.trendingTvRecyclerView.apply {
            adapter = tvAdapter
        }
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.trendingTvRecyclerView)
    }

    private fun setupMovieAdapter(){
        movieAdapter = TrendingMovieAdapter()
        binding.trendingMoviesRecyclerView.apply {
            adapter = movieAdapter
        }
    }

    private fun setupPeopleAdapter(){
        peopleAdapter = TrendingPeopleAdapter()
        binding.trendingPeopleRecyclerView.apply {
            adapter = peopleAdapter
        }
    }

    private fun getTrendingTv(){
        setupTvAdapter()
        viewModel.getTrendingTv()
        lifecycleScope.launch {
            viewModel.tvList.collect{ tvAdapter.differ.submitList(it) }
        }
    }

    private fun getTrendingMovies(){
        setupMovieAdapter()
        viewModel.getTrendingMovies()
        lifecycleScope.launch {
            viewModel.movieList.collect{ movieAdapter.differ.submitList(it) }
        }
    }

    private fun getTrendingPeople(){
        setupPeopleAdapter()
        viewModel.getTrendingPeople()
        lifecycleScope.launch{
            viewModel.peopleList.collect{ peopleAdapter.differ.submitList(it) }
        }
    }

    private fun onMovieClick(){
        movieAdapter.onMovieClick = { data ->
           val fragment = MovieDetailsFragment()
           val bundle = Bundle()
           bundle.putInt("movie_Id",data.id)
           fragment.arguments = bundle
            findNavController().navigate(R.id.action_homePageFragment_to_movieDetailsFragment,bundle)
        }
    }


}