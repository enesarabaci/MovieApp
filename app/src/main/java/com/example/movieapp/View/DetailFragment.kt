package com.example.movieapp.View

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.movieapp.Model.Result
import com.example.movieapp.R
import com.example.movieapp.Util.Util
import com.example.movieapp.Util.loadImage
import com.example.movieapp.ViewModel.DetailViewModel
import com.example.movieapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)

        val result = args.data
        setupViews(result)
        viewModel.checkDatabase(result._id)

        setHasOptionsMenu(true)

        viewModel.results?.observe(viewLifecycleOwner, Observer { list ->
            if (list.isNotEmpty()) {
                buttonChanges(R.drawable.detail_second_button, R.color.colorGrey, R.string.addedToFavorites)
            }else {
                buttonChanges(R.drawable.detail_first_button, R.color.colorBlackS, R.string.addToFavorites)
            }
        })

        binding.addFavorites.setOnClickListener {
            viewModel.addOrDeleteFavorites(result)
        }
    }

    private fun buttonChanges(resource: Int, color: Int, string: Int) {
        binding.addFavorites.apply {
            setBackgroundResource(resource)
        }
        binding.favoritesTextView.apply {
            setText(string)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setTextColor(resources.getColor(color, resources.newTheme()))
            } else {
                setTextColor(resources.getColor(color))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    private fun setupViews(result: Result) {
        binding.apply {
            detailAverage.text = result.vote_average.toString()
            detailBackground.loadImage("${Util.IMAGE_BASE_URL}${result.backdrop_path}")
            detailCount.text = result.vote_count.toString()
            detailDate.text = result.release_date
            detailDescription.text = result.overview
            detailForeground.loadImage("${Util.IMAGE_BASE_URL}${result.poster_path}")
            detailTitle.text = result.original_title
        }
    }

}