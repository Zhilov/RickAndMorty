package com.example.rickandmorty

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rickandmorty.sections.character.FragmentCharacter
import com.example.rickandmorty.sections.character.FragmentCharacterFilter
import com.example.rickandmorty.sections.episode.FragmentEpisode
import com.example.rickandmorty.sections.location.FragmentLocation
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar

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
                FragmentCharacter.FRAGMENT_CHARACTER_DETAILS_TAG -> navController.navigate(R.id.navigation_character_details, fragment.arguments)
                FragmentCharacter.FRAGMENT_CHARACTER_FILTER_TAG -> navController.navigate(R.id.navigation_character_filter, fragment.arguments)
                FragmentLocation.FRAGMENT_LOCATION_TAG -> navController.navigate(R.id.navigation_location_details, fragment.arguments)
                FragmentEpisode.FRAGMENT_EPISODE_TAG -> navController.navigate(R.id.navigation_episode_details, fragment.arguments)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navHostFragment: NavHostFragment? =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment?

        when(navHostFragment!!.childFragmentManager.fragments[0]::class){
            FragmentCharacter::class -> navigate(FragmentCharacterFilter(), FragmentCharacter.FRAGMENT_CHARACTER_FILTER_TAG)
            FragmentLocation::class -> Toast.makeText(this, "Location is not ready", Toast.LENGTH_SHORT).show()
            FragmentEpisode::class -> Toast.makeText(this, "Episode is not ready", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

//    override fun onBackPressed() {
//        supportFragmentManager.popBackStack()
//    }
}