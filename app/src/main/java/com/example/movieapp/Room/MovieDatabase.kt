package com.example.movieapp.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.Model.Result

@Database(entities = arrayOf(Result::class), version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun roomDao(): RoomDao

}