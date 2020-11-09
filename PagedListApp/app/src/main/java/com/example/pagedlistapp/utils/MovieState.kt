package com.example.pagedlistapp.utils

import com.example.kino.model.movie.Movie
import com.example.pagedlistapp.view_model.MovieListViewModel

sealed class MovieState () {
    object ShowLoading : MovieState()
    object HideLoading : MovieState()
    object ShowPageLoading: MovieState()
    object HidePageLoading: MovieState()
    data class Result(val moviesList: List<Movie>?) : MovieState()
}