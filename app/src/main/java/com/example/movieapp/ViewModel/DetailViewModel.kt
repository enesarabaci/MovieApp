package com.example.movieapp.ViewModel

import androidx.lifecycle.*
import com.example.movieapp.Model.Result
import com.example.movieapp.Repo.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repo: MainRepository) : ViewModel() {

    private var resultsFlow: Flow<List<Result>>? = null
    private var _results: LiveData<List<Result>>? = null
    val results: LiveData<List<Result>>?
        get() = _results


    fun checkDatabase(id: Int) {
        resultsFlow = repo.getMovie(id.toString())
        _results = resultsFlow?.asLiveData()
    }

    fun addOrDeleteFavorites(result: Result) {
        println("work: addOrDelete")
        _results?.value?.let {
            println("work: notNull")
            if (it.isNotEmpty()) {
                println("work: notEmpty")
                deleteFromFavorites(it.get(0).Id)
            }else {
                println("work: empty")
                addToFavorites(result)
            }
        }
    }

    private fun addToFavorites(result: Result) {
        println("work: add")
        viewModelScope.launch {
            repo.saveMovie(result)
        }
    }

    private fun deleteFromFavorites(id: Int) {
        println("work: delete")
        viewModelScope.launch {
            repo.deleteMovie(id.toString())
        }
    }

}