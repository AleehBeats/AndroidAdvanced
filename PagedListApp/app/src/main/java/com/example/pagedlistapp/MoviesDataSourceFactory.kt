package com.example.pagedlistapp

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.kino.model.movie.Movie
import com.example.pagedlistapp.model.Movies
import com.example.pagedlistapp.repository.MovieRepository
import io.reactivex.disposables.CompositeDisposable
import kotlin.coroutines.CoroutineContext

class MoviesDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val movieRepository: MovieRepository
) :
    DataSource.Factory<Int, Movie>() {
    val moviesDataSourceLiveData = MutableLiveData<MovieDataSource>()
    private lateinit var moviesDataSource: MovieDataSource
    override fun create(): DataSource<Int, Movie> {
        moviesDataSource = MovieDataSource(
            compositeDisposable = compositeDisposable,
            movieRepository = movieRepository
        )
        moviesDataSourceLiveData.postValue(moviesDataSource)
        return moviesDataSource
    }

    fun invalidate() {
        moviesDataSource.invalidate()
    }
}