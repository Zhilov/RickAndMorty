package com.example.rickandmorty.sections.character

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.retrofit.Common
import com.example.rickandmorty.retrofit.RetrofitServices
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterViewModel : ViewModel(){
    private var mService: RetrofitServices = Common.retrofitService
    var character = MutableLiveData<Character>(null)
    var characters = MutableLiveData<Characters>(null)

    fun getCharacter(id: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            mService.getCharacter(id).enqueue(object: Callback<Character>{
                override fun onResponse(call: Call<Character>, response: Response<Character>) {
                    character.postValue(response.body())
                }

                override fun onFailure(call: Call<Character>, t: Throwable) {

                }

            })
        }
    }

    fun getCharacters(page: Int){
//        getObsCharacters(page).subscribeOn(Schedulers.newThread())
//            .subscribe{
//                characters.postValue(it)
//            }

        viewModelScope.launch(Dispatchers.IO) {
            mService.getAllCharacters(page).enqueue(object: Callback<Characters>{
                override fun onResponse(call: Call<Characters>, response: Response<Characters>) {
                    characters.postValue(response.body())
                }

                override fun onFailure(call: Call<Characters>, t: Throwable) {

                }

            })
        }
    }
}