package com.example.movieapp.Repo

import com.example.movieapp.Model.Movie
import com.example.movieapp.Model.Result
import com.example.movieapp.MovieApi
import com.example.movieapp.Room.RoomDao
import com.example.movieapp.Util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.Exception

class Repository @Inject constructor(
    val movieApi: MovieApi,
    val dao: RoomDao
) : MainRepository {

    override suspend fun getMovie(
        api: String,
        query: String
    ): Resource<Movie> {
        return try {
            val response = movieApi.getMovie(api, query)
            val result = response.body()

            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Error!")
        }
    }

    override suspend fun getTrends(api: String): Resource<Movie> {
        return try {
            val response = movieApi.getTrends(api)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Error!")
        }
    }

    override suspend fun saveMovie(result: Result) {
        dao.saveMovie(result)
    }

    override fun getMovies(query: String): Flow<List<Result>> {
        return dao.getMovies(query)
    }

    override fun getMovie(id: String): Flow<List<Result>> {
        return dao.getMovie(id)
    }

    override suspend fun deleteMovie(id: String) {
        dao.deleteMovie(id)
    }
}