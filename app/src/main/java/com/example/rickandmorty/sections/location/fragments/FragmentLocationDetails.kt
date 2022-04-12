package com.example.rickandmorty.sections.location.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.rickandmorty.R
import com.example.rickandmorty.sections.location.Location


class FragmentLocationDetails : Fragment(R.layout.fragment_location_details) {

    private lateinit var location: Location
    private lateinit var textName: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getLocationFromArguments()
        bindView(location)
    }

    private fun getLocationFromArguments() {
        arguments?.let {
            it.getSerializable(KEY_LOCATION_DETAILS)?.let {
                if (it is Location) {
                    location = it
                } else {
                    error("Location should be provided in args")
                }
            }
        } ?: error("Location should be provided in args")
    }

    private fun bindView(location: Location) {
        findViews()
        textName.text = location.name

    }

    private fun findViews() {
        requireView().run {
            textName = findViewById(R.id.text_location_details_name)

        }
    }

    companion object {
        private const val KEY_LOCATION_DETAILS = "key.location.details"
        const val FRAGMENT_LOCATION_DETAILS_TAG = "FRAGMENT_LOCATION_DETAILS_TAG"

        fun newInstance(location: Location): FragmentLocationDetails {
            val bundle = bundleOf(KEY_LOCATION_DETAILS to location)

            return FragmentLocationDetails().apply { arguments = bundle }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
}