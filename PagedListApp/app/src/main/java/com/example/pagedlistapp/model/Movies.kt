package com.example.pagedlistapp.model

import com.example.kino.model.movie.Movie
import com.google.gson.annotations.SerializedName

data class Movies(
    @SerializedName("results") val movieList: List<Movie>,
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val totalPages: Int?
)
