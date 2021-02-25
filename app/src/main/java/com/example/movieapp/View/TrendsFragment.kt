package com.example.movieapp.View

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.Adapter.MoviesRecyclerAdapter
import com.example.movieapp.MovieEvent
import com.example.movieapp.R
import com.example.movieapp.ViewModel.TrendsViewModel
import com.example.movieapp.databinding.FragmentTrendsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class TrendsFragment : Fragment(R.layout.fragment_trends) {

    private val viewModel: TrendsViewModel by viewModels()
    private lateinit var binding: FragmentTrendsBinding

    @Inject
    lateinit var mradapter: MoviesRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTrendsBinding.bind(view)
        setupRecyclerView()

        setHasOptionsMenu(true)

        mradapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("data", it)
                putString("title", it.original_title)
            }
            findNavController().navigate(R.id.action_trendsFragment_to_detailFragment, bundle)
        }

        lifecycleScope.launchWhenCreated {
            viewModel.data.collect { movieEvent ->
                when (movieEvent) {
                    is MovieEvent.Success -> {
                        binding.trendsProgress.visibility = View.GONE
                        val result = movieEvent.movie.results
                        mradapter.updateList(result)
                    }
                    is MovieEvent.Error -> {
                        binding.trendsProgress.visibility = View.GONE
                        Toast.makeText(requireContext(), movieEvent.message, Toast.LENGTH_LONG).show()
                        println("error")
                    }
                    is MovieEvent.Loading -> {
                        binding.trendsProgress.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    private fun setupRecyclerView() {
        mradapter = MoviesRecyclerAdapter()
        binding.rvTrends.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = mradapter
        }
    }

}