package com.example.rickandmorty.sections.location

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
import com.example.rickandmorty.sections.episode.EpisodeViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class FragmentLocation : Fragment(R.layout.fragment_location) {


    private lateinit var navigator: Navigator
    private lateinit var viewModel: LocationViewModel

    private var locationListAdapter = LocationListAdapter { location ->
        val fragmentLocationDetails = FragmentLocationDetails.newInstance(location)

        navigator.navigate(fragmentLocationDetails, FRAGMENT_LOCATION_TAG)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
        bindList()
        updateList(1)
    }

    private fun updateList(page: Int){
        viewModel.getLocations(page)
        viewModel.locations.observe(viewLifecycleOwner){
            it?.let {
                locationListAdapter.updateList(it.locations)
            }
        }
    }

    private fun bindList() {
        viewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        viewModel.getLocations(1)
        requireView().findViewById<RecyclerView>(R.id.recycler_location).apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            adapter = locationListAdapter
        }
    }

    companion object {
        const val FRAGMENT_LOCATION_TAG = "FRAGMENT_LOCATION_TAG"
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