package com.example.movieapp.View

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.Adapter.MoviesRecyclerAdapter
import com.example.movieapp.R
import com.example.movieapp.ViewModel.SavedViewModel
import com.example.movieapp.databinding.FragmentSavedBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SavedFragment : Fragment(R.layout.fragment_saved) {

    private lateinit var binding: FragmentSavedBinding
    private val viewModel: SavedViewModel by viewModels()

    @Inject
    lateinit var mradapter: MoviesRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedBinding.bind(view)

        setupRecyclerView()

        mradapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("data", it)
                putString("title", it.original_title)
            }
            findNavController().navigate(R.id.action_savedFragment_to_detailFragment, bundle)
        }

        viewModel.results.observe(viewLifecycleOwner, Observer { list ->
            mradapter.updateList(list)
        })

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        val searchItem = menu.findItem(R.id.searchItem)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                p0?.let {
                    viewModel.searchQuery.value = it
                }
                return true
            }
        })
    }

    private fun setupRecyclerView() {
        mradapter = MoviesRecyclerAdapter()
        binding.rvSaved.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = mradapter
        }
    }

}