package com.example.rickandmorty.sections.episode.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
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
    private lateinit var info: TextView
    private lateinit var buttonBack: ImageButton

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
        if (info.text.isEmpty()) info.text = addinfo(episode)
        buttonBack.setOnClickListener {
            navigator.navigateBack()
        }
    }

    private fun addinfo(episode: Episode): StringBuilder {
        val string = StringBuilder()
        val list = ArrayList<String>()
        list.add("Air date: " + episode.air_date)
        list.add("Episode: " + episode.episode)
        episode.created = episode.created.replace("T", "\nTime: ")
        episode.created =
            episode.created.removeRange(episode.created.length - 5, episode.created.length)
        list.add("Created: " + episode.created)

        for (s: String in list) {
            string.append(s + "\n")
        }
        return string
    }

    private fun findViews() {
        requireView().run {
            name = findViewById(R.id.text_episode_details_name)
            info = findViewById(R.id.text_episode_details_info)
            buttonBack = findViewById(R.id.button_episode_details_back)
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