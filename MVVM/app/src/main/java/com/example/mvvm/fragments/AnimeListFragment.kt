package com.example.mvvm.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mvvm.R
import com.example.mvvm.adapters.AnimeListAdapter
import com.example.mvvm.model.Anime
import com.example.mvvm.repository.AnimeRepositoryImpl
import com.example.mvvm.utils.KEY
import com.example.mvvm.view_model.AnimeListViewModel
import org.koin.android.ext.android.inject

class AnimeListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val viewModel: AnimeListViewModel by inject()


    private val animeClickListener = object : AnimeListAdapter.AnimeClickListener {
        override fun animeClick(position: Int, anime: Anime) {
            val bundle = Bundle()
            bundle.putString(KEY, anime.id)
            val animeDetailsFragment = AnimeDetailsFragment()
            animeDetailsFragment.arguments = bundle
            parentFragmentManager.beginTransaction().add(R.id.container, animeDetailsFragment)
                .addToBackStack(null).commit()
        }

    }
    private val adapter by lazy {
        AnimeListAdapter(animeClickListener)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_music_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setAdapter()
        getAnimeList()
    }

    private fun bindViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
    }

    private fun setAdapter() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun getAnimeList() {
        viewModel.getAnimeList()
        viewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is AnimeListViewModel.State.ShowLoading -> {
                    swipeRefreshLayout.isRefreshing = true
                }
                is AnimeListViewModel.State.Result -> {
                    Log.d("anime_list_fragment", result.animeList.toString())
                    adapter.setAnimeList(result.animeList)
                }
                is AnimeListViewModel.State.HideLoading -> {
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        })
    }
}