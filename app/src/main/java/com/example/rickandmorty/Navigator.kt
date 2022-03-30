package com.example.rickandmorty

import androidx.fragment.app.Fragment

interface Navigator {

    fun navigate(fragment: Fragment, tag: String)

}