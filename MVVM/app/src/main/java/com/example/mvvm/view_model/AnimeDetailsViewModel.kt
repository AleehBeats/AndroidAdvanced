package com.example.mvvm.view_model

import androidx.lifecycle.MutableLiveData
import com.example.mvvm.model.Anime
import com.example.mvvm.repository.AnimeRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class AnimeDetailsViewModel(private val animeRepository: AnimeRepository) : BaseViewModel() {

    val liveData = MutableLiveData<State>()

    fun getAnimeDetails(id: String) {
        launch {
            liveData.value = State.ShowLoading
            val anime: Anime? = try {
                animeRepository.getAnimeDetails(id)
            } catch (e: Exception) {
                Anime()
            }
            liveData.value = State.ShowResult(anime)
            liveData.value = State.HideLoading
        }
    }

    sealed class State {
        object ShowLoading : State()
        object HideLoading : State()
        data class ShowResult(val anime: Anime?) : State()
    }
}