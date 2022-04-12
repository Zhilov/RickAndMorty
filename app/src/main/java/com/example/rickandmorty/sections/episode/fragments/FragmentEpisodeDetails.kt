package com.example.rickandmorty.sections.episode.fragments

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.rickandmorty.Navigator
import com.example.rickandmorty.R
import com.example.rickandmorty.sections.episode.Episode

class FragmentEpisodeDetails : Fragment(R.layout.fragment_episode_details) {

    private lateinit var navigator: Navigator
    private lateinit var episode: Episode
    private lateinit var name: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getEpisodeFromArguments()
        bindView(episode)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> navigator.navigateBack()
        }
        return super.onOptionsItemSelected(item)
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

    companion object {
        const val FRAGMENT_EPISODE_DETAILS_TAG = "FRAGMENT_EPISODE_DETAILS_TAG"
        private const val KEY_EPISODE_DETAILS = "key.episode.details"

        fun newInstance(episode: Episode): FragmentEpisodeDetails {
            val bundle = bundleOf(KEY_EPISODE_DETAILS to episode)
            return FragmentEpisodeDetails().apply { arguments = bundle }
        }
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