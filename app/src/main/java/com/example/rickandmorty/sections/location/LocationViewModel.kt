package com.example.rickandmorty.sections.location

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.retrofit.Common
import com.example.rickandmorty.retrofit.RetrofitServices
import com.example.rickandmorty.sections.episode.Episodes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationViewModel : ViewModel() {
    private var mService: RetrofitServices = Common.retrofitService
    var location = MutableLiveData<Location>(null)
    var locations = MutableLiveData<Locations>(null)

    fun getLocations(page: Int) {
        mService.getAllLocations(page).enqueue(object : Callback<Locations> {
            override fun onResponse(call: Call<Locations>, response: Response<Locations>) {
                locations.postValue(response.body())
            }

            override fun onFailure(call: Call<Locations>, t: Throwable) {

            }

        })
    }

    fun getLocationsWithFilter(
        filters: HashMap<String, String>,
    ) {
        mService.getLocationsWithFilter(filters)
            .enqueue(object : Callback<Locations> {
                override fun onResponse(
                    call: Call<Locations>,
                    response: Response<Locations>,
                ) {
                    locations.postValue(response.body())
                }

                override fun onFailure(call: Call<Locations>, t: Throwable) {
                    Log.d("TAG", "Error occurred")
                }

            })
    }
}