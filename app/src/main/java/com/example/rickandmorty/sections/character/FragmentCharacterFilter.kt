package com.example.rickandmorty.sections.character

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.Navigator
import com.example.rickandmorty.R
import com.google.android.material.textfield.TextInputLayout


class FragmentCharacterFilter: Fragment(R.layout.fragment_character_filter){

    private lateinit var navigator: Navigator
    private lateinit var viewModel: CharacterViewModel
    private lateinit var name: TextInputLayout
    private lateinit var species: TextInputLayout
    private lateinit var type: TextInputLayout
    private lateinit var status: Spinner
    private lateinit var gender: Spinner
    private lateinit var button: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindList()

        button.setOnClickListener{
            Toast.makeText(requireContext(), "Hey!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun bindList() {
        viewModel = ViewModelProvider(this).get(CharacterViewModel::class.java)
        viewModel.getCharacters(1)
        name = requireView().findViewById(R.id.character_filter_input_name)
        species = requireView().findViewById(R.id.character_filter_input_species)
        type = requireView().findViewById(R.id.character_filter_input_type)
        status = requireActivity().findViewById(R.id.character_filter_spinner_status)
        gender = requireActivity().findViewById(R.id.character_filter_spinner_gender)
        button = requireActivity().findViewById(R.id.character_filter_button_apply)

        status.adapter = ArrayAdapter<String>(requireContext(), R.layout.fragment_character_filter_spinner_item, resources.getStringArray(R.array.character_spinner_status))
        gender.adapter = ArrayAdapter<String>(requireContext(), R.layout.fragment_character_filter_spinner_item, resources.getStringArray(R.array.character_spinner_gender))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    companion object {

    }
}