package com.gharieb.movie_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearSnapHelper
import com.gharieb.movie_app.R
import com.gharieb.movie_app.adapters.homeAdapters.TrendingTvAdapter
import com.gharieb.movie_app.databinding.FragmentHomePageBinding
import com.gharieb.movie_app.databinding.FragmentSearchBinding
import com.gharieb.movie_app.viewModels.HomeViewModel
import com.gharieb.movie_app.viewModels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: TrendingTvAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSearchedItems()

    }

    private fun setupSearchAdapter(){
        searchAdapter = TrendingTvAdapter()
        binding.searchedItemsRecyclerView.apply {
            adapter = searchAdapter
        }
    }

    private fun getSearchedItems(){
        setupSearchAdapter()
        var job: Job? = null
        binding.searchBar.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                //showProgressBar()

                editable.let {
                    if (editable.toString().isNotEmpty()){
                        delay(1000)
                        viewModel.getSearchedItem(editable.toString())
                        //hideProgressBar()
                    }else if (editable.toString().isEmpty()){
                        //hideProgressBar()
                        searchAdapter.differ.submitList(null)
                        //hideNoResults()
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.searchedItemList.collect{ searchAdapter.differ.submitList(it) }
        }
    }



}