package com.example.rickandmorty.sections.episode

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.retrofit.Common
import com.example.rickandmorty.retrofit.RetrofitServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EpisodeViewModel : ViewModel() {
    private var mService: RetrofitServices = Common.retrofitService
    var episode = MutableLiveData<Episode>(null)
    var episodes = MutableLiveData<Episodes>(null)

    fun getEpisodes(page: Int) {
        mService.getAllEpisodes(page).enqueue(object : Callback<Episodes> {
            override fun onResponse(call: Call<Episodes>, response: Response<Episodes>) {
                episodes.postValue(response.body())
            }

            override fun onFailure(call: Call<Episodes>, t: Throwable) {

            }

        })
    }

    fun getEpisodesWithFilter(
        filters: HashMap<String, String>,
    ) {
        mService.getEpisodesWithFilter(filters)
            .enqueue(object : Callback<Episodes> {
                override fun onResponse(
                    call: Call<Episodes>,
                    response: Response<Episodes>,
                ) {
                    episodes.postValue(response.body())
                }

                override fun onFailure(call: Call<Episodes>, t: Throwable) {
                    Log.d("TAG", "Error occurred")
                }

            })
    }
}