package com.gharieb.movie_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.gharieb.movie_app.R
import com.gharieb.movie_app.adapters.CategoriesAdapter
import com.gharieb.movie_app.adapters.ProductionCompaniesAdapter
import com.gharieb.movie_app.adapters.TrendingMovieAdapter
import com.gharieb.movie_app.databinding.FragmentMovieDetailsBinding
import com.gharieb.movie_app.viewModels.MovieDetailsViewModel
import com.gharieb.movie_app.viewModels.MyListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailsBinding
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()
    private val myListViewModel: MyListViewModel by viewModels()
    private lateinit var genreAdapter: CategoriesAdapter
    private lateinit var productionCompaniesAdapter: ProductionCompaniesAdapter
    private lateinit var moreLikeThisAdapter: TrendingMovieAdapter
    private var movieId: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCategoriesAdapter()
        setupMoviesByCategoryAdapter()
        setupProductionCompaniesAdapter()
        getMovieId()
        onMoreLikeThisClick()
        fetchData(movieId)
        binding.backArrowButton.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun setupCategoriesAdapter(){
        genreAdapter = CategoriesAdapter()
        binding.genreRecyclerView.apply {
            adapter = genreAdapter
        }
    }

    private fun setupMoviesByCategoryAdapter(){
        moreLikeThisAdapter = TrendingMovieAdapter()
        binding.moreLikeThisRecyclerView.apply {
            adapter = moreLikeThisAdapter
        }
    }

    private fun setupProductionCompaniesAdapter(){
        productionCompaniesAdapter = ProductionCompaniesAdapter()
        binding.productionCompaniesRecyclerView.apply {
            adapter = productionCompaniesAdapter
        }
    }

    private fun fetchData(movieId: Int){
        movieDetailsViewModel.fetchMovieById(movieId)
        movieDetailsViewModel.movie.observe(viewLifecycleOwner) { data ->
            Glide.with(requireContext())
                .load("https://image.tmdb.org/t/p/w154" + data.poster_path)
                .into(binding.image)
            binding.title.text = data.title
            binding.overview.text = data.overview
            binding.rate.text = data.vote_average.toString()
            genreAdapter.differ.submitList(data.genres)
            productionCompaniesAdapter.differ.submitList(data.production_companies)
            myListViewModel.getMoviesByCategory(data.genres[0].id)
            lifecycleScope.launch {
                myListViewModel.movieByCategoryList.collect{ moreLikeThisAdapter.differ.submitList(it) }
            }
        }
    }

    private fun getMovieId(){
        val data = arguments
        if (data != null){
            movieId = data.getInt("movie_Id")
        }
    }

    private fun onMoreLikeThisClick(){
        moreLikeThisAdapter.onMovieClick = { data ->
            val fragment = MovieDetailsFragment()
            val bundle = Bundle()
            bundle.putInt("movie_Id",data.id)
            fragment.arguments = bundle
            findNavController().navigate(R.id.movieDetailsFragment,bundle)
        }
    }

    
}