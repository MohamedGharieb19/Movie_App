package com.gharieb.movie_app.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gharieb.movie_app.R
import com.gharieb.movie_app.adapters.SearchAdapter
import com.gharieb.movie_app.databinding.FragmentSearchBinding
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
    private lateinit var searchAdapter: SearchAdapter
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
        setEditTextOnFocusMode()
        getSearchedItems()
        onMovieClick()

        hideNoResults()
        hideProgressBar()

    }

    private fun setupSearchAdapter(){
        searchAdapter = SearchAdapter()
        binding.searchedItemsRecyclerView.apply {
            layoutManager = GridLayoutManager(context,2, RecyclerView.VERTICAL,false)
            adapter = searchAdapter
        }
    }

    private fun setEditTextOnFocusMode(){
        binding.searchBar.requestFocus()
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.searchBar, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun getSearchedItems(){
        setupSearchAdapter()
        var job: Job? = null
        binding.searchBar.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                showProgressBar()

                editable.let {
                    if (editable.toString().isNotEmpty()){
                        delay(1000)
                        viewModel.getSearchedItem(editable.toString())
                        hideProgressBar()
                    }else if (editable.toString().isEmpty()){
                        hideProgressBar()
                        searchAdapter.differ.submitList(null)
                        hideNoResults()
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.searchedItemList.collect{ data ->
                if (data.isEmpty()){
                    showNoResults()
                    searchAdapter.differ.submitList(null)
                }else if (data.isNotEmpty()){
                    hideNoResults()
                    searchAdapter.differ.submitList(data)
                }
                hideProgressBar()
            }
        }
    }

    private fun onMovieClick(){
        searchAdapter.onMovieClick = { data ->
            val fragment = MovieDetailsFragment()
            val bundle = Bundle()
            bundle.putInt("movie_Id",data.id)
            fragment.arguments = bundle
            findNavController().navigate(R.id.movieDetailsFragment,bundle)
        }
    }

    private fun hideProgressBar() {
        binding.ProgressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar() {
        binding.ProgressBar.visibility = View.VISIBLE
    }
    private fun showNoResults(){
        binding.backgroundImageView.visibility = View.VISIBLE
        binding.resultsTextView.visibility = View.VISIBLE
    }
    private fun hideNoResults(){
        binding.backgroundImageView.visibility = View.INVISIBLE
        binding.resultsTextView.visibility = View.INVISIBLE
    }

}