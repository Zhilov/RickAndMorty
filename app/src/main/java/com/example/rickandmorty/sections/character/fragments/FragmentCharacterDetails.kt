package com.example.rickandmorty.sections.character.fragments

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.rickandmorty.Navigator
import com.example.rickandmorty.R
import com.example.rickandmorty.sections.character.Character
import com.squareup.picasso.Picasso

class FragmentCharacterDetails : Fragment(R.layout.fragment_character_details) {

    private lateinit var navigator: Navigator
    private lateinit var buttonBack: ImageButton
    private lateinit var character: Character
    private lateinit var imageView: ImageView
    private lateinit var textName: TextView
    private lateinit var textInfo: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getContactFromArguments()
        bindView(character)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> Toast.makeText(requireContext(), "Finish!", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getContactFromArguments() {
        arguments?.let {
            it.getSerializable(KEY_CHARACTER_DETAILS)?.let {
                if (it is Character) {
                    character = it
                } else {
                    error("Contact should be provided in args")
                }
            }
        } ?: error("Contact should be provided in args")
    }

    private fun bindView(character: Character) {
        findViews()
        Picasso.get().load(character.image).into(imageView)
        textName.text = character.name
        if (textInfo.text.isEmpty()) textInfo.text = addinfo(character)

        buttonBack.setOnClickListener {
            navigator.navigateBack()
        }
    }

    private fun addinfo(character: Character): StringBuilder {

        val string = StringBuilder()
        val list = ArrayList<String>()
        list.add("Status: " + character.status)
        list.add("Species: " + character.species)
        if (character.type.isNotEmpty()) list.add("Type: " + character.type)
        list.add("Gender: " + character.gender)
        character.created = character.created.replace("T", "\nTime: ")
        character.created =
            character.created.removeRange(character.created.length - 5, character.created.length)
        list.add("Created: " + character.created)

        for (s: String in list) {
            string.append(s + "\n")
        }
        return string
    }

    private fun findViews() {
        requireView().run {
            buttonBack = findViewById(R.id.button_character_details_back)
            imageView = findViewById(R.id.image_character_details)
            textName = findViewById(R.id.text_character_details_name)
            textInfo = findViewById(R.id.text_character_details_info)
        }
    }

    companion object {
        const val FRAGMENT_CHARACTER_DETAILS_TAG = "FRAGMENT_CHARACTER_DETAILS_TAG"
        private const val KEY_CHARACTER_DETAILS = "key.character.details"

        fun newInstance(character: Character): FragmentCharacterDetails {
            val bundle = bundleOf(KEY_CHARACTER_DETAILS to character)

            return FragmentCharacterDetails().apply { arguments = bundle }
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
}