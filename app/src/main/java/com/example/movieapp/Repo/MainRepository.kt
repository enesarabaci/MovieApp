package com.example.movieapp.Repo

import com.example.movieapp.Model.Movie
import com.example.movieapp.Model.Result
import com.example.movieapp.Util.Resource
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun getMovie(
        api: String,
        query: String
    ): Resource<Movie>

    suspend fun getTrends(
        api: String
    ): Resource<Movie>

    suspend fun saveMovie(result: Result)

    fun getMovies(query: String): Flow<List<Result>>

    fun getMovie(id: String) : Flow<List<Result>>

    suspend fun deleteMovie(id: String)

}