package com.example.mvvm.utils

import com.example.mvvm.model.Anime
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApi {
    @GET("films")
    suspend fun getAnimeList(): Response<List<Anime>>

    @GET("films/{id}")
    suspend fun getAnimeDetails(
        @Path("id") id: String
    ): Response<Anime>
}