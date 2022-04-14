package com.example.rickandmorty.sections.location.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.rickandmorty.Navigator
import com.example.rickandmorty.R
import com.example.rickandmorty.sections.location.Location

class FragmentLocationDetails : Fragment(R.layout.fragment_location_details) {

    private lateinit var navigator: Navigator
    private lateinit var location: Location
    private lateinit var buttonBack: ImageButton
    private lateinit var name: TextView
    private lateinit var info: TextView

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
        name.text = location.name
        info.text = addinfo(location)

        buttonBack.setOnClickListener {
            navigator.navigateBack()
        }
    }

    private fun findViews() {
        requireView().run {
            buttonBack = findViewById(R.id.button_location_details_back)
            name = findViewById(R.id.text_location_details_name)
            info = findViewById(R.id.text_location_details_info)
        }
    }

    private fun addinfo(location: Location): StringBuilder {
        val string = StringBuilder()
        val list = ArrayList<String>()
        list.add("Type: " + location.type)
        list.add("Dimension: " + location.dimension)
        location.created = location.created.replace("T", "\nTime: ")
        location.created =
            location.created.removeRange(location.created.length - 5, location.created.length)
        list.add("Created: " + location.created)

        for (s: String in list) {
            string.append(s + "\n")
        }
        return string
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
        private const val KEY_LOCATION_DETAILS = "key.location.details"
        const val FRAGMENT_LOCATION_DETAILS_TAG = "FRAGMENT_LOCATION_DETAILS_TAG"

        fun newInstance(location: Location): FragmentLocationDetails {
            val bundle = bundleOf(KEY_LOCATION_DETAILS to location)

            return FragmentLocationDetails().apply { arguments = bundle }
        }
    }
}