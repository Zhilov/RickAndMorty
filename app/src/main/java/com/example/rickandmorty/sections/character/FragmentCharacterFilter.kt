package com.example.rickandmorty.sections.character

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.Navigator
import com.example.rickandmorty.R
import com.google.android.material.textfield.TextInputEditText

class FragmentCharacterFilter: Fragment(R.layout.fragment_character_filter){

    private lateinit var navigator: Navigator
    private lateinit var viewModel: CharacterViewModel
    private lateinit var name: TextInputEditText
    private lateinit var species: TextInputEditText
    private lateinit var type: TextInputEditText
    private lateinit var status: Spinner
    private lateinit var gender: Spinner

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun bindList() {
        viewModel = ViewModelProvider(this).get(CharacterViewModel::class.java)
        viewModel.getCharacters(1)
        name = requireView().findViewById(R.id.character_filter_input_name)
        species = requireView().findViewById(R.id.character_filter_input_species)
        type = requireView().findViewById(R.id.character_filter_input_type)
        status = requireActivity().findViewById(R.id.character_filter_spinner_status)
        gender = requireActivity().findViewById(R.id.character_filter_spinner_gender)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    companion object {

    }
}