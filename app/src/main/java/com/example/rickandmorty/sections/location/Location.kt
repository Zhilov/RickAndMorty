package com.example.rickandmorty.sections.location

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class Locations(
    @SerializedName("results")
    var locations: List<Location>
)

@Parcelize
data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String,
): Parcelable, Serializable