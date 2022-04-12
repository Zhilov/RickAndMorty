package com.example.rickandmorty.sections.character.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.rickandmorty.Navigator
import com.example.rickandmorty.R

class FragmentCharacterFilter : Fragment(R.layout.fragment_character_filter) {

    private lateinit var navigator: Navigator
    private lateinit var name: EditText
    private lateinit var species: EditText
    private lateinit var type: EditText
    private lateinit var status: Spinner
    private lateinit var gender: Spinner
    private lateinit var button: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindList()

        button.setOnClickListener {

            val map: HashMap<String, String> = HashMap()

            if (name.text.isNotEmpty()) map["name"] = name.text.toString()
            if (species.text.isNotEmpty()) map["species"] = species.text.toString()
            if (type.text.isNotEmpty()) map["type"] = type.text.toString()
            if (status.selectedItemPosition > 0) map["status"] = status.selectedItem.toString()
            if (gender.selectedItemPosition > 0) map["gender"] = gender.selectedItem.toString()

            navigator.navigateBack()
            navigator.navigate(FragmentCharacter.newInstance(map),
                FragmentCharacter.FRAGMENT_CHARACTER_TAG)
        }
    }

    private fun bindList() {
        name = requireView().findViewById(R.id.character_filter_edit_name)
        species = requireView().findViewById(R.id.character_filter_edit_species)
        type = requireView().findViewById(R.id.character_filter_edit_type)
        status = requireActivity().findViewById(R.id.character_filter_spinner_status)
        gender = requireActivity().findViewById(R.id.character_filter_spinner_gender)
        button = requireActivity().findViewById(R.id.character_filter_button_apply)

        status.adapter = ArrayAdapter(requireContext(),
            R.layout.fragment_character_filter_spinner_item,
            resources.getStringArray(R.array.character_spinner_status))
        gender.adapter = ArrayAdapter(requireContext(),
            R.layout.fragment_character_filter_spinner_item,
            resources.getStringArray(R.array.character_spinner_gender
            ))
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
        const val FRAGMENT_CHARACTER_FILTER_TAG = "FRAGMENT_CHARACTER_FILTER_TAG"
    }
}