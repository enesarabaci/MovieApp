package com.example.movieapp.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "MovieTable")
data class Result(
    val backdrop_path: String,
    @SerializedName("id")
    val _id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val vote_average: Double,
    val vote_count: Int,
    @PrimaryKey(autoGenerate = true)
    val Id: Int = 0
) : Serializable