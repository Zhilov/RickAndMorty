package com.example.rickandmorty.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.rickandmorty.R

class FragmentLocation : Fragment(R.layout.fragment_location) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        const val FRAGMENT_LOCATION_TAG = "FRAGMENT_LOCATION_TAG"

        fun newInstance() = FragmentLocation()
    }
}