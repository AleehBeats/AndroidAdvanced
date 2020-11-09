package com.example.pagedlistapp.view_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.example.kino.model.movie.Movie
import com.example.pagedlistapp.MovieDataSource
import com.example.pagedlistapp.MoviesDataSourceFactory
import com.example.pagedlistapp.repository.MovieRepository
import com.example.pagedlistapp.utils.API_KEY
import com.example.pagedlistapp.utils.ApiResponse
import com.example.pagedlistapp.utils.MovieState
import com.mobile.telecomapp.utils.SingleLiveEvent
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieListViewModel(
    private val context: Context,
    private var movieRepository: MovieRepository
) : BaseViewModel() {
    private val pagedListConfig: PagedList.Config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(MovieDataSource.PAGE_SIZE * 2)
        .setPageSize(MovieDataSource.PAGE_SIZE)
        .setPrefetchDistance(10)
        .build()
    
    lateinit var liveData: LiveData<MovieState>

    private lateinit var moviesDataSourceFactory: MoviesDataSourceFactory
    private val _moviesPagesList = SingleLiveEvent<PagedList<Movie>>()
    val moviesPagesList: LiveData<PagedList<Movie>> = _moviesPagesList
    fun getMovies() {
        moviesDataSourceFactory = MoviesDataSourceFactory(
            compositeDisposable = disposable,
            movieRepository = movieRepository
        )
        liveData = Transformations.switchMap(
            moviesDataSourceFactory.moviesDataSourceLiveData,
            MovieDataSource::getMutableLiveData
        )
        addDisposable(
            RxPagedListBuilder(moviesDataSourceFactory, pagedListConfig)
                .setFetchScheduler(Schedulers.io())
                .buildFlowable(BackpressureStrategy.BUFFER)
                .cache()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    _moviesPagesList.postValue(result)
                    Log.d("result_list", result.toString())
                }, {})
        )
    }

    fun clearAll() = moviesDataSourceFactory.invalidate()
}