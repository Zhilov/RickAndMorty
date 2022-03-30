package com.example.rickandmorty.sections.location

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.Navigator
import com.example.rickandmorty.R
import com.example.rickandmorty.retrofit.Common
import com.example.rickandmorty.retrofit.RetrofitServices
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class FragmentLocation : Fragment(R.layout.fragment_location) {

    companion object {
        const val FRAGMENT_LOCATION_TAG = "FRAGMENT_LOCATION_TAG"

        fun newInstance() = FragmentLocation()
    }
    private lateinit var navigator: Navigator
    private lateinit var mService: RetrofitServices

    private var locationListAdapter = LocationListAdapter { location ->
        val fragmentLocationDetails = FragmentLocationDetails.newInstance(location)

        navigator.navigate(fragmentLocationDetails, FRAGMENT_LOCATION_TAG)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is Navigator) {
            navigator = context
        } else {
            error("Host should implement Navigator interface")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mService = Common.retrofitService
        bindView()
    }

    private fun bindView() {
        bindList()

        getAllLocations(1).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                locationListAdapter.updateList(it.locations)
            }
    }

    private fun bindList() {
        requireView().findViewById<RecyclerView>(R.id.recycler_location).apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            adapter = locationListAdapter
        }
    }

    private fun getAllLocations(page: Int): Observable<Locations> {
        return Observable.create {
            it.onNext(mService.getAllLocations(page).execute().body())
            it.onComplete()
        }
    }

    //    private fun findViews() {
//        requireView().run {
//        }
//    }
}