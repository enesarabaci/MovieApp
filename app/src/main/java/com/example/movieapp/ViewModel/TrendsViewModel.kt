package com.example.movieapp.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.MovieEvent
import com.example.movieapp.Repo.MainRepository
import com.example.movieapp.Util.Resource
import com.example.movieapp.Util.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendsViewModel @Inject constructor(private val repo: MainRepository) : ViewModel() {

    private val _data = MutableStateFlow<MovieEvent>(MovieEvent.Empty)
    val data: MutableStateFlow<MovieEvent>
        get() = _data

    init {
        _data.value = MovieEvent.Loading
        viewModelScope.launch {
            val resource = repo.getTrends(Util.API)
            when (resource) {
                is Resource.Success -> {
                    _data.value = MovieEvent.Success(resource.data!!)
                }
                is Resource.Error -> {
                    _data.value = MovieEvent.Error(resource.message!!)
                }
            }
        }
    }


}