package com.example.pagedlistapp.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kino.model.movie.Movie
import com.example.pagedlistapp.R
import com.example.pagedlistapp.utils.IMAGE_URL
import com.example.pagedlistapp.utils.MovieState
import com.squareup.picasso.Picasso

class FilmsAdapter : PagedListAdapter<Movie, FilmsAdapter.MainViewHolder>(diffCallback) {
    private var movieState: MovieState? = null

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.main_item, parent, false)
        )
    }


    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class MainViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
            val tvReleaseDate = view.findViewById<TextView>(R.id.tvReleaseDate)
            val poster = view.findViewById<ImageView>(R.id.ivPoster)
            val tvVotesCount = view.findViewById<TextView>(R.id.tvVotesCount)
            val tvRating = view.findViewById<TextView>(R.id.tvRating)
            if (movie != null) {

                tvTitle.text = movie.title
                tvReleaseDate.text = movie.releaseDate.substring(0, 4)
                tvVotesCount.text = movie.voteCount.toString()
                tvRating.text = movie.voteAverage.toString()

                Picasso.get()
                    .load(IMAGE_URL + movie.posterPath)
                    .into(poster)
            }
        }
    }

    fun clearAll() {
        submitList(null)
        notifyDataSetChanged()
    }

    fun setNetworkState(newExerciseState: MovieState) {
        val previousState: MovieState? = this.movieState
        val previousExtraRow = hasExtraRow()
        this.movieState = newExerciseState
        val newExtraRow = hasExtraRow()
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (newExtraRow && previousState !== newExerciseState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    private fun hasExtraRow(): Boolean {
        return movieState != null && movieState !== MovieState.HideLoading
    }
}