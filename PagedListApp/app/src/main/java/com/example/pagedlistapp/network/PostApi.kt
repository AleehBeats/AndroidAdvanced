package com.example.pagedlistapp.network

import com.example.pagedlistapp.model.Movies
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApi {
    @GET("movie/top_rated")
    fun getMovieList(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Single<Response<Movies>>
}