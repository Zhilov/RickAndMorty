package com.example.rickandmorty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rickandmorty.sections.character.FragmentCharacter
import com.example.rickandmorty.sections.episode.FragmentEpisode
import com.example.rickandmorty.sections.location.FragmentLocation
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_RickAndMorty)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_character, R.id.navigation_location, R.id.navigation_episode))
        setupActionBarWithNavController(navController, appBarConfiguration)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.nav_view)
        bottomNavigationView.setupWithNavController(navController)

    }

    override fun navigate(fragment: Fragment, tag: String){
            when(tag){
                FragmentCharacter.FRAGMENT_CHARACTER_TAG -> navController.navigate(R.id.navigation_character_details, fragment.arguments)
                FragmentLocation.FRAGMENT_LOCATION_TAG -> navController.navigate(R.id.navigation_location_details, fragment.arguments)
                FragmentEpisode.FRAGMENT_EPISODE_TAG -> navController.navigate(R.id.navigation_episode_details, fragment.arguments)
            }
    }

//    override fun onBackPressed() {
//        supportFragmentManager.popBackStack()
//    }
}