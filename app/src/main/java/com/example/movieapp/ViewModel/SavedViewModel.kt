package com.example.movieapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.movieapp.Model.Result
import com.example.movieapp.Repo.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(private val repo: MainRepository) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    @ExperimentalCoroutinesApi
    private val resultsFlow = searchQuery.flatMapLatest {
        repo.getMovies(it)
    }
    @ExperimentalCoroutinesApi
    val results: LiveData<List<Result>> = resultsFlow.asLiveData()


}