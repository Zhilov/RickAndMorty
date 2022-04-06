package com.example.rickandmorty.sections.location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.retrofit.Common
import com.example.rickandmorty.retrofit.RetrofitServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationViewModel : ViewModel(){
    private var mService: RetrofitServices = Common.retrofitService
    var location = MutableLiveData<Location>(null)
    var locations = MutableLiveData<Locations>(null)

    fun getLocation(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            mService.getLocation(id).enqueue(object: Callback<Location> {
                override fun onResponse(call: Call<Location>, response: Response<Location>) {
                    location.postValue(response.body())
                }

                override fun onFailure(call: Call<Location>, t: Throwable) {

                }

            })
        }
    }

    fun getLocations(page: Int){
        viewModelScope.launch(Dispatchers.IO) {
            mService.getAllLocations(page).enqueue(object: Callback<Locations> {
                override fun onResponse(call: Call<Locations>, response: Response<Locations>) {
                    locations.postValue(response.body())
                }

                override fun onFailure(call: Call<Locations>, t: Throwable) {

                }

            })
        }
    }
}