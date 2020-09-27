package com.example.mvvm.model

import com.google.gson.annotations.SerializedName

data class Anime(
    @SerializedName("id") var id: String? = "",
    @SerializedName("title") var title: String? = "",
    @SerializedName("description") var desc: String? = "",
    @SerializedName("director") var director: String? = "",
    @SerializedName("producer") var producer: String? = "",
    @SerializedName("released_date") var releasedDate: Int? = 0,
    @SerializedName("rt_score") var rating: Int? = 0
)