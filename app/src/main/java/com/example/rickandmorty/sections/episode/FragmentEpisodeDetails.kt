package com.example.rickandmorty.sections.episode

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.rickandmorty.R

private const val KEY_EPISODE_DETAILS = "key.episode.details"

class FragmentEpisodeDetails : Fragment(R.layout.fragment_episode_details) {

    companion object {
        fun newInstance(episode: Episode): FragmentEpisodeDetails {
            val bundle = bundleOf(KEY_EPISODE_DETAILS to episode)
            return FragmentEpisodeDetails().apply { arguments = bundle }
        }
    }

    private lateinit var episode: Episode
    private lateinit var name: TextView

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getEpisodeFromArguments()
        bindView(episode)
    }

    private fun getEpisodeFromArguments() {
        arguments?.let {
            it.getSerializable(KEY_EPISODE_DETAILS)?.let {
                if (it is Episode) {
                    episode = it
                } else {
                    error("Episode should be provided in args")
                }
            }
        } ?: error("Episode should be provided in args")
    }

    private fun bindView(episode: Episode) {
        findViews()
        name.text = episode.name
    }

    private fun findViews() {
        requireView().run {
            name = findViewById(R.id.text_episode_details_name)

        }
    }
}