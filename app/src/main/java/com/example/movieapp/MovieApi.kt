package com.example.movieapp

import com.example.movieapp.Model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("search/movie?language=en")
    suspend fun getMovie(
        @Query("api_key") api: String,
        @Query("query") query: String
    ): Response<Movie>

    @GET("trending/movie/day?language=en")
    suspend fun getTrends(
        @Query("api_key") api: String
    ): Response<Movie>

}