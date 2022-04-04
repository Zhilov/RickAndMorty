package com.example.rickandmorty.sections.episode

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.Navigator
import com.example.rickandmorty.R
import com.example.rickandmorty.retrofit.Common
import com.example.rickandmorty.retrofit.RetrofitServices
import com.example.rickandmorty.sections.character.CharacterViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class FragmentEpisode : Fragment(R.layout.fragment_episode) {

    private lateinit var navigator: Navigator
    private lateinit var viewModel: EpisodeViewModel

    private var episodeListAdapter = EpisodeListAdapter { episode ->
        val fragmentEpisodeDetails = FragmentEpisodeDetails.newInstance(episode)

        navigator.navigate(fragmentEpisodeDetails, FRAGMENT_EPISODE_TAG)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindList()
        updateList(1)
    }

    private fun updateList(page: Int){
        viewModel.getEpisodes(page)
        viewModel.episodes.observe(this){
            it?.let {
                episodeListAdapter.updateList(it.episodes)
            }
        }
    }

    private fun bindList() {
        viewModel = ViewModelProvider(this).get(EpisodeViewModel::class.java)
        viewModel.getEpisodes(1)
        requireView().findViewById<RecyclerView>(R.id.recycler_episode).apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            adapter = episodeListAdapter
        }
    }

    companion object {
        const val FRAGMENT_EPISODE_TAG = "FRAGMENT_EPISODE_TAG"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is Navigator) {
            navigator = context
        } else {
            error("Host should implement Navigator interface")
        }
    }
}