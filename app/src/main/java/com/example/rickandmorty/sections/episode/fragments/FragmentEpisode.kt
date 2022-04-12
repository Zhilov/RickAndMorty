package com.example.rickandmorty.sections.episode.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.rickandmorty.Navigator
import com.example.rickandmorty.R
import com.example.rickandmorty.sections.episode.EpisodeListAdapter
import com.example.rickandmorty.sections.episode.EpisodeViewModel

class FragmentEpisode : Fragment(R.layout.fragment_episode) {

    private lateinit var navigator: Navigator
    private lateinit var viewModel: EpisodeViewModel

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var buttonSearch: ImageButton


    private var episodeListAdapter = EpisodeListAdapter { episode ->
        val fragmentEpisodeDetails = FragmentEpisodeDetails.newInstance(episode)
        navigator.navigate(fragmentEpisodeDetails,
            FragmentEpisodeDetails.FRAGMENT_EPISODE_DETAILS_TAG)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindList()
        if (arguments != null) {
            getFilterFromArguments()
        } else {
            updateList(1)
        }

        buttonSearch.setOnClickListener {
            navigator.navigate(FragmentEpisodeFilter(),
                FragmentEpisodeFilter.FRAGMENT_EPISODE_FILTER_TAG)
        }

        swipeRefreshLayout.setOnRefreshListener {
            updateList(1)
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun getFilterFromArguments() {
        arguments?.let {
            it.getSerializable(KEY_EPISODE_FILTER)?.let {
                if (it is HashMap<*, *>) {
                    viewModel.getEpisodesWithFilter(it as HashMap<String, String>)
                    viewModel.episodes.observe(viewLifecycleOwner) {
                        it?.let {
                            episodeListAdapter.updateList(it.episodes)
                            progressBar.visibility = View.INVISIBLE
                        }
                    }
                } else {
                    return
                }
            }
        } ?: return
    }

    private fun updateList(page: Int) {
        viewModel.getEpisodes(page)
        viewModel.episodes.observe(viewLifecycleOwner) {
            it?.let {
                episodeListAdapter.updateList(it.episodes)
                progressBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun bindList() {
        viewModel = ViewModelProvider(this).get(EpisodeViewModel::class.java)
        swipeRefreshLayout = requireView().findViewById(R.id.refresh_episode)
        progressBar = requireView().findViewById(R.id.progress_episode)
        progressBar.visibility = View.VISIBLE
        buttonSearch = requireView().findViewById(R.id.button_episode_search)
        requireView().findViewById<RecyclerView>(R.id.recycler_episode).apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            adapter = episodeListAdapter
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

    companion object {

        fun newInstance(map: HashMap<String, String>): FragmentEpisode {
            val bundle = bundleOf(KEY_EPISODE_FILTER to map)

            return FragmentEpisode().apply { arguments = bundle }
        }

        const val FRAGMENT_EPISODE_TAG = "FRAGMENT_EPISODE_TAG"

        private const val KEY_EPISODE_FILTER = "key.episode.filter"
    }
}