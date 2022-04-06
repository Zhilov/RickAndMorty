package com.example.rickandmorty.sections.character

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.rickandmorty.Navigator
import com.example.rickandmorty.R
import com.example.rickandmorty.retrofit.Common
import com.example.rickandmorty.retrofit.RetrofitServices
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class FragmentCharacter : Fragment(R.layout.fragment_character) {

    private lateinit var navigator: Navigator
    private lateinit var viewModel: CharacterViewModel

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar

    private var characterListAdapter = CharacterListAdapter { character ->
        val fragmentCharacterDetails = FragmentCharacterDetails.newInstance(character)
        navigator.navigate(fragmentCharacterDetails, FRAGMENT_CHARACTER_DETAILS_TAG)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindList()
        getFilterFromArguments()
        updateList(1)

        swipeRefreshLayout.setOnRefreshListener {
            updateList(2)
            false.also { swipeRefreshLayout.isRefreshing = it }
        }
    }

    private fun getFilterFromArguments() {
        arguments?.let {
            it.getSerializable(Companion.KEY_CHARACTER_FILTER)?.let { it ->
                if (it is Character) {
                    viewModel.getCharacter(1)
                } else {
                    return
                }
            }
        } ?: return
    }

    private fun updateList(page: Int) {
        viewModel.getCharacters(page)
        viewModel.characters.observe(this) {
            it?.let {
                characterListAdapter.updateList(it.characters)
                progressBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun bindList() {
        viewModel = ViewModelProvider(this).get(CharacterViewModel::class.java)
        viewModel.getCharacters(1)
        swipeRefreshLayout = requireView().findViewById(R.id.character_refresh)
        progressBar = requireView().findViewById(R.id.progress_character)
        progressBar.visibility = View.VISIBLE
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
        const val FRAGMENT_CHARACTER_DETAILS_TAG = "FRAGMENT_CHARACTER_DETAILS_TAG"
        const val FRAGMENT_CHARACTER_FILTER_TAG = "FRAGMENT_CHARACTER_FILTER_TAG"
        private const val KEY_CHARACTER_FILTER = "key.character.filter"
    }
}