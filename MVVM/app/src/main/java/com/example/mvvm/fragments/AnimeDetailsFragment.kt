package com.example.mvvm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.mvvm.R
import com.example.mvvm.model.Anime
import com.example.mvvm.utils.KEY
import com.example.mvvm.view_model.AnimeDetailsViewModel
import org.koin.android.ext.android.inject
import org.w3c.dom.Text

class AnimeDetailsFragment : Fragment() {

    private lateinit var tvDirectorName: TextView
    private lateinit var tvRatingNumber: TextView
    private lateinit var tvProducer: TextView
    private lateinit var tvReleasedDate: TextView
    private lateinit var tvDescription: TextView
    private lateinit var tvTitle: TextView
    private val viewModel: AnimeDetailsViewModel by inject()

    private var animeId: String = ""
    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        animeId = args?.getString(KEY).toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_anime_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindViews(view)
        getAnime(animeId)
    }

    private fun bindViews(view: View) = with(view) {
        tvDirectorName = findViewById(R.id.tvDirectorName)
        tvRatingNumber = findViewById(R.id.tvRatingNumber)
        tvProducer = findViewById(R.id.tvProducer)
        tvReleasedDate = findViewById(R.id.tvReleasedDate)
        tvDescription = findViewById(R.id.tvDescription)
        tvTitle = findViewById(R.id.tvTitle)
    }

    private fun setData(anime: Anime?) {
        tvDirectorName.text = anime?.director
        tvReleasedDate.text = anime?.releasedDate.toString()
        tvDescription.text = anime?.desc
        tvProducer.text = anime?.producer
        tvRatingNumber.text = anime?.rating.toString()
        tvTitle.text = anime?.title
    }

    private fun getAnime(animeId: String) {
        viewModel.getAnimeDetails(animeId)
        viewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is AnimeDetailsViewModel.State.ShowLoading -> {

                }
                is AnimeDetailsViewModel.State.ShowResult -> {
                    setData(result.anime)
                }
                is AnimeDetailsViewModel.State.HideLoading -> {

                }
            }
        })
    }
}