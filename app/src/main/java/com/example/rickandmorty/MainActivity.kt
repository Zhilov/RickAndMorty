package com.example.rickandmorty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rickandmorty.sections.character.fragments.FragmentCharacter
import com.example.rickandmorty.sections.character.fragments.FragmentCharacterDetails
import com.example.rickandmorty.sections.character.fragments.FragmentCharacterFilter
import com.example.rickandmorty.sections.episode.fragments.FragmentEpisode
import com.example.rickandmorty.sections.episode.fragments.FragmentEpisodeDetails
import com.example.rickandmorty.sections.episode.fragments.FragmentEpisodeFilter
import com.example.rickandmorty.sections.location.fragments.FragmentLocation
import com.example.rickandmorty.sections.location.fragments.FragmentLocationDetails
import com.example.rickandmorty.sections.location.fragments.FragmentLocationFilter
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_RickAndMorty)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment_activity_main)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.nav_view)
        bottomNavigationView.setupWithNavController(navController)

    }

    override fun navigate(fragment: Fragment, tag: String) {
        when (tag) {
            FragmentCharacter.FRAGMENT_CHARACTER_TAG -> navController.navigate(R.id.navigation_character,
                fragment.arguments)
            FragmentCharacterDetails.FRAGMENT_CHARACTER_DETAILS_TAG -> navController.navigate(R.id.navigation_character_details,
                fragment.arguments)
            FragmentCharacterFilter.FRAGMENT_CHARACTER_FILTER_TAG -> navController.navigate(R.id.navigation_character_filter,
                fragment.arguments)

            FragmentLocation.FRAGMENT_LOCATION_TAG -> navController.navigate(R.id.navigation_location,
                fragment.arguments)
            FragmentLocationDetails.FRAGMENT_LOCATION_DETAILS_TAG -> navController.navigate(R.id.navigation_location_details,
                fragment.arguments)
            FragmentLocationFilter.FRAGMENT_LOCATION_FILTER_TAG -> navController.navigate(R.id.navigation_location_filter,
                fragment.arguments)

            FragmentEpisode.FRAGMENT_EPISODE_TAG -> navController.navigate(R.id.navigation_episode,
                fragment.arguments)
            FragmentEpisodeDetails.FRAGMENT_EPISODE_DETAILS_TAG -> navController.navigate(R.id.navigation_episode_details,
                fragment.arguments)
            FragmentEpisodeFilter.FRAGMENT_EPISODE_FILTER_TAG -> navController.navigate(R.id.navigation_episode_filter,
                fragment.arguments)

        }
    }

    override fun navigateBack() {
        navController.popBackStack()
    }
}