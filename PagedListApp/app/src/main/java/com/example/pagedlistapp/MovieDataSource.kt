package com.example.pagedlistapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.kino.model.movie.Movie
import com.example.pagedlistapp.model.Movies
import com.example.pagedlistapp.repository.MovieRepository
import com.example.pagedlistapp.utils.API_KEY
import com.example.pagedlistapp.utils.ApiResponse
import com.example.pagedlistapp.utils.MovieState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(
    private val compositeDisposable: CompositeDisposable,
    private val movieRepository: MovieRepository
) :
    PageKeyedDataSource<Int, Movie>() {
    private val _liveData = MutableLiveData<MovieState>()
    private var list: MutableList<Movie> = mutableListOf()
    companion object {
        const val PAGE_SIZE = 20
        private const val FIRST_PAGE = 1
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        movieRepository.getRemoteMovieList(API_KEY, FIRST_PAGE)?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.doOnSubscribe { _liveData.postValue(MovieState.ShowLoading) }
            ?.doFinally {
                _liveData.postValue(MovieState.HidePageLoading)
                _liveData.postValue(MovieState.HideLoading)
            }
            ?.subscribe { result ->
                if (result is ApiResponse.Success) {
                    val movieList = result.result
                    list = movieList.toMutableList()
                    Log.d("result_list", result.toString())
                    callback.onResult(movieList.toMutableList(), null, FIRST_PAGE + 1)
                }
                else if (result is ApiResponse.Error){
                    Log.d("result_list", result.toString())
                }
            }?.let {
                compositeDisposable.add(
                    it
                )
            }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        movieRepository.getRemoteMovieList(API_KEY, params.key)?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.doOnSubscribe {
                _liveData.postValue(MovieState.ShowPageLoading)
            }
            ?.doFinally {
                _liveData.postValue(MovieState.HidePageLoading)
                _liveData.postValue(MovieState.HideLoading)
            }
            ?.subscribe { result ->
                if(result is ApiResponse.Success){
                    callback.onResult(result.result, params.key + 1)
                }
            }?.let {
                compositeDisposable.add(
                    it
            )
            }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }
    fun getMutableLiveData() = _liveData
}