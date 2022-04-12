package com.example.rickandmorty.sections.character.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.rickandmorty.Navigator
import com.example.rickandmorty.R
import com.example.rickandmorty.sections.character.CharacterListAdapter
import com.example.rickandmorty.sections.character.CharacterViewModel

class FragmentCharacter : Fragment(R.layout.fragment_character) {

    private lateinit var navigator: Navigator
    private lateinit var viewModel: CharacterViewModel

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var buttonSearch: ImageButton


    private var characterListAdapter = CharacterListAdapter { character ->
        val fragmentCharacterDetails = FragmentCharacterDetails.newInstance(character)
        navigator.navigate(fragmentCharacterDetails,
            FragmentCharacterDetails.FRAGMENT_CHARACTER_DETAILS_TAG)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindList()
        if (arguments != null) {
            getFilterFromArguments()
        } else {
            updateList(1)
        }

        buttonSearch.setOnClickListener {
            navigator.navigate(FragmentCharacterFilter(),
                FragmentCharacterFilter.FRAGMENT_CHARACTER_FILTER_TAG)
        }

        swipeRefreshLayout.setOnRefreshListener {
            updateList(1)
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun getFilterFromArguments() {
        arguments?.let {
            it.getSerializable(KEY_CHARACTER_FILTER)?.let {
                if (it is HashMap<*, *>) {
                    viewModel.getCharactersWithFilter(it as HashMap<String, String>)
                    viewModel.characters.observe(viewLifecycleOwner) {
                        it?.let {
                            characterListAdapter.updateList(it.characters)
                            progressBar.visibility = View.INVISIBLE
                        }
                    }
                } else {
                    return
                }
            }
        } ?: return
    }

    private fun updateList(page: Int) {
        viewModel.getCharacters(page)
        viewModel.characters.observe(viewLifecycleOwner) {
            it?.let {
                characterListAdapter.updateList(it.characters)
                progressBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun bindList() {
        viewModel = ViewModelProvider(this).get(CharacterViewModel::class.java)
        swipeRefreshLayout = requireView().findViewById(R.id.refresh_character)
        progressBar = requireView().findViewById(R.id.progress_character)
        progressBar.visibility = View.VISIBLE
        buttonSearch = requireView().findViewById(R.id.button_character_search)
        requireView().findViewById<RecyclerView>(R.id.recycler_character).apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            adapter = characterListAdapter
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

    companion object {

        fun newInstance(map: HashMap<String, String>): FragmentCharacter {
            val bundle = bundleOf(KEY_CHARACTER_FILTER to map)

            return FragmentCharacter().apply { arguments = bundle }
        }

        const val FRAGMENT_CHARACTER_TAG = "FRAGMENT_CHARACTER_TAG"

        private const val KEY_CHARACTER_FILTER = "key.character.filter"
    }
}