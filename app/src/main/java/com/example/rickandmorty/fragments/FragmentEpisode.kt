package com.example.rickandmorty.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.rickandmorty.R

class FragmentEpisode : Fragment(R.layout.fragment_episode) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        const val FRAGMENT_EPISODE_TAG = "FRAGMENT_EPISODE_TAG"

        fun newInstance() = FragmentEpisode()
    }
}