package com.example.mvvm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.R
import com.example.mvvm.model.Anime

class AnimeListAdapter(private val animeClickListener: AnimeClickListener) :
    RecyclerView.Adapter<AnimeListAdapter.MusicViewHolder>() {

    private var animeList: MutableList<Anime> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MusicViewHolder(inflater.inflate(R.layout.anime_item, parent, false))
    }

    override fun getItemCount(): Int {
        return animeList.size
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.bind(animeList[position])
    }

    fun setAnimeList(list: List<Anime>?) {
        if (list != null) {
            animeList.addAll(list)
        }
        notifyDataSetChanged()
    }

    inner class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(anime: Anime) = with(itemView) {
            val tvTitle = findViewById<TextView>(R.id.tvTitle)
            val tvRatingNumber = findViewById<TextView>(R.id.tvRatingNumber)
            val tvDirectorName = findViewById<TextView>(R.id.tvDirectorName)

            tvTitle.text = anime.title
            tvRatingNumber.text = anime.rating.toString()
            tvDirectorName.text = anime.director

            itemView.setOnClickListener {
                animeClickListener.animeClick(adapterPosition, anime)
            }

        }
    }

    interface AnimeClickListener {
        fun animeClick(position: Int, anime: Anime)
    }
}