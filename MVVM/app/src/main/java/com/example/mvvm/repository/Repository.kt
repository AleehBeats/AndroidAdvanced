package com.example.mvvm.repository

import com.example.mvvm.model.Anime
import com.example.mvvm.utils.PostApi

interface AnimeRepository {
    suspend fun getAnimeList(): List<Anime>?
    suspend fun getAnimeDetails(id: String): Anime?
}

class AnimeRepositoryImpl(
    private var service: PostApi? = null
) : AnimeRepository {
    override suspend fun getAnimeList(): List<Anime>? {
        return service?.getAnimeList()?.body()
    }

    override suspend fun getAnimeDetails(id: String): Anime? {
        return service?.getAnimeDetails(id)?.body()
    }

}