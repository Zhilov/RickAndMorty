package com.example.rickandmorty.sections.episode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.retrofit.Common
import com.example.rickandmorty.retrofit.RetrofitServices
import com.example.rickandmorty.sections.character.Character
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EpisodeViewModel : ViewModel(){
    private var mService: RetrofitServices = Common.retrofitService
    var episode = MutableLiveData<Episode>(null)
    var episodes = MutableLiveData<Episodes>(null)

    fun getEpisode(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            mService.getEpisode(id).enqueue(object: Callback<Episode> {
                override fun onResponse(call: Call<Episode>, response: Response<Episode>) {
                    episode.postValue(response.body())
                }

                override fun onFailure(call: Call<Episode>, t: Throwable) {

                }

            })
        }
    }

    fun getEpisodes(page: Int){
        viewModelScope.launch(Dispatchers.IO) {
            mService.getAllEpisodes(page).enqueue(object: Callback<Episodes> {
                override fun onResponse(call: Call<Episodes>, response: Response<Episodes>) {
                    episodes.postValue(response.body())
                }

                override fun onFailure(call: Call<Episodes>, t: Throwable) {

                }

            })
        }
    }
}