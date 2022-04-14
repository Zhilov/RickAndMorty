package com.example.rickandmorty.retrofit

object Common {
    private const val BASE_URL = "https://rickandmortyapi.com/"
    val retrofitService: RetrofitServices
        get() =
            RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}