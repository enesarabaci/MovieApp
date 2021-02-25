package com.example.movieapp.View

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.Adapter.MoviesRecyclerAdapter
import com.example.movieapp.MovieEvent
import com.example.movieapp.R
import com.example.movieapp.ViewModel.SearchViewModel
import com.example.movieapp.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchView: SearchView

    @Inject
    lateinit var mradapter: MoviesRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        setupRecyclerView()

        setHasOptionsMenu(true)

        mradapter.setOnItemClickListener {
            println(it.overview)
            val bundle = Bundle().apply {
                putSerializable("data", it)
                putString("title", it.original_title)
            }
            findNavController().navigate(R.id.action_searchFragment_to_detailFragment, bundle)
        }

        lifecycleScope.launchWhenCreated {
            viewModel.data.collect { movieEvent ->
                when (movieEvent) {
                    is MovieEvent.Success -> {
                        binding.searchProgress.visibility = View.GONE
                        val result = movieEvent.movie.results
                        mradapter.updateList(result)
                    }
                    is MovieEvent.Error -> {
                        binding.searchProgress.visibility = View.GONE
                        Toast.makeText(requireContext(), movieEvent.message, Toast.LENGTH_LONG).show()
                    }
                    is MovieEvent.Loading -> {
                        binding.searchProgress.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        var job: Job? = null

        val searchItem = menu.findItem(R.id.searchItem)
        searchView = searchItem.actionView as androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                job?.cancel()
                p0?.let {
                    if (it.isNotEmpty()) {
                        job = MainScope().launch {
                            delay(1000)
                            viewModel.searchMovie(it)
                        }
                    }else {
                        mradapter.clearList()
                    }
                }
                return true
            }
        })
    }

    private fun setupRecyclerView() {
        binding.rvSearch.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = mradapter
        }
    }

}