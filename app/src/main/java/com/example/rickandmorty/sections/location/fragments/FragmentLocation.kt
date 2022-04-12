package com.example.rickandmorty.sections.location.fragments

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
import com.example.rickandmorty.sections.location.LocationListAdapter
import com.example.rickandmorty.sections.location.LocationViewModel

class FragmentLocation : Fragment(R.layout.fragment_location) {

    private lateinit var navigator: Navigator
    private lateinit var viewModel: LocationViewModel

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var buttonSearch: ImageButton


    private var locationListAdapter = LocationListAdapter { location ->
        val fragmentLocationDetails = FragmentLocationDetails.newInstance(location)
        navigator.navigate(fragmentLocationDetails,
            FragmentLocationDetails.FRAGMENT_LOCATION_DETAILS_TAG)
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
            navigator.navigate(FragmentLocationFilter(),
                FragmentLocationFilter.FRAGMENT_LOCATION_FILTER_TAG)
        }

        swipeRefreshLayout.setOnRefreshListener {
            updateList(1)
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun getFilterFromArguments() {
        arguments?.let {
            it.getSerializable(KEY_LOCATION_FILTER)?.let {
                if (it is HashMap<*, *>) {
                    viewModel.getLocationsWithFilter(it as HashMap<String, String>)
                    viewModel.locations.observe(viewLifecycleOwner) {
                        it?.let {
                            locationListAdapter.updateList(it.locations)
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
        viewModel.getLocations(page)
        viewModel.locations.observe(viewLifecycleOwner) {
            it?.let {
                locationListAdapter.updateList(it.locations)
                progressBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun bindList() {
        viewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        swipeRefreshLayout = requireView().findViewById(R.id.refresh_location)
        progressBar = requireView().findViewById(R.id.progress_location)
        progressBar.visibility = View.VISIBLE
        buttonSearch = requireView().findViewById(R.id.button_location_search)
        requireView().findViewById<RecyclerView>(R.id.recycler_location).apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            adapter = locationListAdapter
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

        fun newInstance(map: HashMap<String, String>): FragmentLocation {
            val bundle = bundleOf(KEY_LOCATION_FILTER to map)

            return FragmentLocation().apply { arguments = bundle }
        }

        const val FRAGMENT_LOCATION_TAG = "FRAGMENT_LOCATION_TAG"

        private const val KEY_LOCATION_FILTER = "key.location.filter"
    }
}