package com.example.rickandmorty.sections.episode

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class Episodes(
    @SerializedName("results")
    var episodes: List<Episode>,
)

@Parcelize
data class Episode(
    val id: Int,
    val name: String,
    val air_date: String,
    var episode: String,
    val characters: List<String>,
    val url: String,
    var created: String,
) : Parcelable, Serializable