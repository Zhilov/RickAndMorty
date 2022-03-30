package com.example.rickandmorty.sections.character

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class Characters(
    @SerializedName("results")
    var characters: List<Character>
)

@Parcelize
data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String,
): Parcelable, Serializable

@Parcelize
data class Origin(
    val name: String,
    val url: String,
): Parcelable, Serializable

data class Location(
    val name: String,
    val url: String,
)