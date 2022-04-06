package com.example.rickandmorty.sections.character

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.rickandmorty.R
import com.squareup.picasso.Picasso

private const val KEY_CHARACTER_DETAILS = "key.character.details"

class FragmentCharacterDetails : Fragment(R.layout.fragment_character_details) {

    companion object {
        const val FRAGMENT_CHARACTER_DETAILS_TAG = "FRAGMENT_CHARACTER_DETAILS_TAG"

        fun newInstance(character: Character): FragmentCharacterDetails {
            val bundle = bundleOf(KEY_CHARACTER_DETAILS to character)

            return FragmentCharacterDetails().apply { arguments = bundle }
        }
    }

    private lateinit var character: Character
    private lateinit var imageView: ImageView
    private lateinit var textName: TextView
    private lateinit var textSpecies: TextView
    private lateinit var textStatus: TextView
    private lateinit var textGender: TextView

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getContactFromArguments()
        bindView(character)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
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
        textSpecies.text = character.species
        textStatus.text = character.status
        textGender.text = character.gender
    }

    private fun findViews() {
        requireView().run {
            imageView = findViewById(R.id.image_character_details)
            textName = findViewById(R.id.text_character_details_name)
            textSpecies = findViewById(R.id.text_character_details_species)
            textStatus = findViewById(R.id.text_character_details_status)
            textGender = findViewById(R.id.text_character_details_gender)

//            findViewById<View>(R.id.btn_save).setOnClickListener {
//                if (checkFields()) {
//                    editContact(Contact(contact.id, inputName.text.toString(), inputPhone.text.toString()))
//                } else {
//                    //Show message
//                }
//            }
        }
    }
}