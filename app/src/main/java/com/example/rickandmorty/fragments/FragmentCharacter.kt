package com.example.rickandmorty.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.rickandmorty.R
import com.example.rickandmorty.retrofit.Common
import com.example.rickandmorty.retrofit.RetrofitServices

class FragmentCharacter : Fragment(R.layout.fragment_character) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var x = "hh"

        Thread(Runnable {
            val mService: RetrofitServices = Common.retrofitService
            mService.getApi().execute().body()
        }).start()

        Toast.makeText(requireContext(), "hey $x", Toast.LENGTH_SHORT).show()

    }

    companion object {
        const val FRAGMENT_CHARACTER_TAG = "FRAGMENT_CHARACTER_TAG"

        fun newInstance() = FragmentCharacter()
    }
}