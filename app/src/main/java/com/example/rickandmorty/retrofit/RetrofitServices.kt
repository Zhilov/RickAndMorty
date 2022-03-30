package com.example.rickandmorty.retrofit

import com.example.rickandmorty.sections.character.Character
import com.example.rickandmorty.sections.character.Characters
import com.example.rickandmorty.sections.character.Location
import com.example.rickandmorty.sections.episode.Episode
import com.example.rickandmorty.sections.episode.Episodes
import com.example.rickandmorty.sections.location.Locations
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitServices {

    //Character

    @GET("api/character")
    fun getAllCharacters(@Query ("page") page: Int): Call<Characters>

    @GET("api/character/{id}")
    fun getCharacter(@Path("id") id: Int): Call<Character>


    //Location

    @GET("api/location")
    fun getAllLocations(@Query("page") page: Int): Call<Locations>

    @GET("api/location/{id}")
    fun getLocation(@Path("id") id: Int): Call<Location>


    //Episode

    @GET("api/episode")
    fun getAllEpisodes(@Query("page") page: Int): Call<Episodes>

    @GET("api/episode/{id}")
    fun getEpisode(@Path("id") id: Int): Call<Episode>

}