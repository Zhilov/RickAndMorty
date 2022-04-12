package com.example.rickandmorty.sections.episode.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.rickandmorty.Navigator
import com.example.rickandmorty.R

class FragmentEpisodeFilter : Fragment(R.layout.fragment_episode_filter) {

    private lateinit var navigator: Navigator
    private lateinit var name: EditText
    private lateinit var episode: EditText
    private lateinit var button: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindList()

        button.setOnClickListener {

            val map: HashMap<String, String> = HashMap()


            if (name.text.isNotEmpty()) map["name"] = name.text.toString()
            if (episode.text.isNotEmpty()) map["episode"] = episode.text.toString()

            navigator.navigateBack()
            navigator.navigate(FragmentEpisode.newInstance(map),
                FragmentEpisode.FRAGMENT_EPISODE_TAG)
        }
    }

    private fun bindList() {
        name = requireView().findViewById(R.id.episode_filter_edit_name)
        episode = requireView().findViewById(R.id.episode_filter_edit_episode)
        button = requireActivity().findViewById(R.id.episode_filter_button_apply)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Navigator) {
            navigator = context
        } else {
            error("Host should implement Navigator interface")
        }
    }

    companion object {
        const val FRAGMENT_EPISODE_FILTER_TAG = "FRAGMENT_EPISODE_FILTER_TAG"
    }
}