package com.example.pagedlistapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pagedlistapp.R
import com.example.pagedlistapp.utils.MovieState
import com.example.pagedlistapp.view_model.MovieListViewModel
import org.koin.android.ext.android.inject

class FilmsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val viewModel: MovieListViewModel by inject()
    private val filmsAdapter: FilmsAdapter by lazy {
        FilmsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.films_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = filmsAdapter
        swipeRefreshLayout.setOnRefreshListener {
            filmsAdapter.clearAll()
            setData()
            swipeRefreshLayout.isRefreshing = false
        }
        setData()
    }

    private fun setData() {
        viewModel.getMovies()
        viewModel.moviesPagesList.observe(viewLifecycleOwner, Observer { result ->
            filmsAdapter.submitList(result)
        })
        viewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
            filmsAdapter.setNetworkState(result)
            when (result) {
                is MovieState.ShowLoading -> {
                    swipeRefreshLayout.isRefreshing = true
                }
                is MovieState.HideLoading -> {
                    swipeRefreshLayout.isRefreshing = false
                }
                else -> {
                }
            }
        })
    }

}