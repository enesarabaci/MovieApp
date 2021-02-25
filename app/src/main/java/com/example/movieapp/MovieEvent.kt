package com.example.movieapp

import com.example.movieapp.Model.Movie

sealed class MovieEvent {
    class Success(val movie: Movie) : MovieEvent()
    class Error(val message: String) : MovieEvent()
    object Loading : MovieEvent()
    object Empty : MovieEvent()
}
