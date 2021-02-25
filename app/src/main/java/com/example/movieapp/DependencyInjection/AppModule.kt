package com.example.movieapp.DependencyInjection

import android.content.Context
import androidx.room.Room
import com.example.movieapp.MovieApi
import com.example.movieapp.Repo.MainRepository
import com.example.movieapp.Repo.Repository
import com.example.movieapp.Room.MovieDatabase
import com.example.movieapp.Room.RoomDao
import com.example.movieapp.Util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getRetrofitApi(): MovieApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieApi::class.java)

    @Singleton
    @Provides
    fun getMovieDatabse(@ApplicationContext context: Context): MovieDatabase =
        Room.databaseBuilder(context, MovieDatabase::class.java, "movie_database").build()

    @Singleton
    @Provides
    fun getRoomDao(movieDatabase: MovieDatabase): RoomDao = movieDatabase.roomDao()

    @Singleton
    @Provides
    fun getRepo(api: MovieApi, dao: RoomDao): MainRepository = Repository(api, dao)

}