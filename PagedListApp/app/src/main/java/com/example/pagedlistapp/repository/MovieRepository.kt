package com.example.pagedlistapp.repository

import android.util.Log
import com.example.kino.model.movie.Movie
import com.example.pagedlistapp.network.PostApi
import com.example.pagedlistapp.utils.ApiResponse
import io.reactivex.Single

interface MovieRepository {
    fun getRemoteMovieList(apiKey: String, page: Int): Single<ApiResponse<List<Movie>>>?
}

class MovieRepositoryImpl(private var service: PostApi? = null) : MovieRepository {
    override fun getRemoteMovieList(apiKey: String, page: Int): Single<ApiResponse<List<Movie>>>? {
        return service?.getMovieList(apiKey, page)?.map { response ->
            if (response.isSuccessful) {
                val list = response.body()?.movieList ?: emptyList()
                Log.d("result_list", list.toString())
                ApiResponse.Success<List<Movie>>(list)
            } else {
                ApiResponse.Error<List<Movie>>("Response error")
            }
        }
    }

}