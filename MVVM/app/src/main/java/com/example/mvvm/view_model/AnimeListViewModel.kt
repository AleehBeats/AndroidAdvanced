package com.example.mvvm.view_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm.model.Anime
import com.example.mvvm.repository.AnimeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class AnimeListViewModel(
    private var context: Context,
    private var animeRepository: AnimeRepository
) : BaseViewModel() {

    val liveData = MutableLiveData<State>()
    fun getAnimeList() {
        launch {
            liveData.value = State.ShowLoading
            val animeList: List<Anime>? = try {
                animeRepository.getAnimeList()
            } catch (e: Exception) {
                Log.d("anime_list_view_model", e.toString())
                emptyList()
            }
            liveData.value = State.Result(animeList)
            liveData.value = State.HideLoading
        }
    }

    sealed class State {
        object ShowLoading : State()
        object HideLoading : State()
        data class Result(val animeList: List<Anime>?) : State()
    }
}