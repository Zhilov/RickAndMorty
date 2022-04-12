package com.example.rickandmorty.sections.character

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.retrofit.Common
import com.example.rickandmorty.retrofit.RetrofitServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterViewModel : ViewModel() {
    private var mService: RetrofitServices = Common.retrofitService
    var character = MutableLiveData<Character>(null)
    var characters = MutableLiveData<Characters>(null)

    fun getCharacters(page: Int) {
        mService.getAllCharacters(page).enqueue(object : Callback<Characters> {
            override fun onResponse(call: Call<Characters>, response: Response<Characters>) {
                characters.postValue(response.body())
            }

            override fun onFailure(call: Call<Characters>, t: Throwable) {
            }

        })
    }

    fun getCharactersWithFilter(
        filters: HashMap<String, String>,
    ) {
        mService.getCharactersWithFilter(filters)
            .enqueue(object : Callback<Characters> {
                override fun onResponse(
                    call: Call<Characters>,
                    response: Response<Characters>,
                ) {
                    characters.postValue(response.body())
                }

                override fun onFailure(call: Call<Characters>, t: Throwable) {
                    Log.d("TAG", "Error occurred")
                }

            })
    }

//    fun getCharacters(page: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            mService.getAllCharacters(page).enqueue(object : Callback<Characters> {
//                override fun onResponse(call: Call<Characters>, response: Response<Characters>) {
//                    characters.postValue(response.body())
//                }
//
//                override fun onFailure(call: Call<Characters>, t: Throwable) {
//
//                }
//
//            })
//        }
//    }
//
//    fun getCharactersWithFilter(
//        filters: HashMap<String, String>,
//    ) {
//        viewModelScope.launch(Dispatchers.IO) {
//            mService.getCharactersWithFilter(filters)
//                .enqueue(object : Callback<Characters> {
//                    override fun onResponse(
//                        call: Call<Characters>,
//                        response: Response<Characters>,
//                    ) {
//                        characters.postValue(response.body())
//                    }
//
//                    override fun onFailure(call: Call<Characters>, t: Throwable) {
//
//                    }
//
//                })
//        }
//    }
}