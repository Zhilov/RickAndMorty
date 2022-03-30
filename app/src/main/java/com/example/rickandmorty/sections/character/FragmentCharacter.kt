package com.example.rickandmorty.sections.character

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.rickandmorty.Navigator
import com.example.rickandmorty.R
import com.example.rickandmorty.RecyclerViewItemDecorator
import com.example.rickandmorty.retrofit.Common
import com.example.rickandmorty.retrofit.RetrofitServices
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class FragmentCharacter : Fragment(R.layout.fragment_character) {

    companion object {
        const val FRAGMENT_CHARACTER_TAG = "FRAGMENT_CHARACTER_TAG"

        fun newInstance() = FragmentCharacter()
    }
    private lateinit var navigator: Navigator
    private lateinit var mService: RetrofitServices
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private var characterListAdapter = CharacterListAdapter { character ->
        val fragmentCharacterDetails = FragmentCharacterDetails.newInstance(character)

        navigator.navigate(fragmentCharacterDetails, FRAGMENT_CHARACTER_TAG)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is Navigator) {
            navigator = context
        } else {
            error("Host should implement Navigator interface")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()

       swipeRefreshLayout.setOnRefreshListener {
            updateList(1)
        }
    }

    private fun bindView() {
        bindList()
        updateList(1)
    }

    private fun updateList(page: Int){
        getAllCharacters(page).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                characterListAdapter.updateList(it.characters)
                if (swipeRefreshLayout.isRefreshing){
                    swipeRefreshLayout.isRefreshing = false
                }
            }
    }

    private fun bindList() {
        mService = Common.retrofitService
        swipeRefreshLayout = requireView().findViewById(R.id.character_refresh)
        requireView().findViewById<RecyclerView>(R.id.recycler_character).apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            adapter = characterListAdapter
        }
    }

    private fun getAllCharacters(page: Int): Observable<Characters> {
        return Observable.create {
                it.onNext(mService.getAllCharacters(page).execute().body())
            it.onComplete()
        }
    }
}