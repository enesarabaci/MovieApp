package com.example.movieapp.Room

import androidx.room.*
import com.example.movieapp.Model.Result
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(result: Result)

    @Query("SELECT * FROM MovieTable WHERE original_title LIKE '%' || :query || '%'")
    fun getMovies(query: String): Flow<List<Result>>

    @Query("SELECT * FROM MovieTable WHERE _id = :id")
    fun getMovie(id: String) : Flow<List<Result>>

    @Query("DELETE FROM MovieTable WHERE Id = :id")
    suspend fun deleteMovie(id: String)

}