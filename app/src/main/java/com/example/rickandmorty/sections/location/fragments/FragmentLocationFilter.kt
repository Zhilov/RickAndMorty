package com.example.rickandmorty.sections.location.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.rickandmorty.Navigator
import com.example.rickandmorty.R

class FragmentLocationFilter : Fragment(R.layout.fragment_location_filter) {

    private lateinit var navigator: Navigator
    private lateinit var name: EditText
    private lateinit var type: EditText
    private lateinit var dimension: EditText
    private lateinit var buttonApply: Button
    private lateinit var buttonBack: ImageButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindList()

        buttonApply.setOnClickListener {

            val map: HashMap<String, String> = HashMap()

            if (name.text.isNotEmpty()) map["name"] = name.text.toString()
            if (type.text.isNotEmpty()) map["type"] = type.text.toString()
            if (dimension.text.isNotEmpty()) map["dimension"] = dimension.text.toString()

            navigator.navigateBack()
            navigator.navigate(FragmentLocation.newInstance(map),
                FragmentLocation.FRAGMENT_LOCATION_TAG)
        }

        buttonBack.setOnClickListener {
            navigator.navigateBack()
        }
    }

    private fun bindList() {
        name = requireView().findViewById(R.id.location_filter_edit_name)
        type = requireView().findViewById(R.id.location_filter_edit_type)
        dimension = requireView().findViewById(R.id.location_filter_edit_dimension)
        buttonApply = requireView().findViewById(R.id.location_filter_button_apply)
        buttonBack = requireView().findViewById(R.id.button_location_filter_back)
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
        const val FRAGMENT_LOCATION_FILTER_TAG = "FRAGMENT_LOCATION_FILTER_TAG"
    }
}