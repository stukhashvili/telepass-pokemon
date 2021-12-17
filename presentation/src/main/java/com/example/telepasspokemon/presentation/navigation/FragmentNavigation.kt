package com.example.telepasspokemon.presentation.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.example.telepasspokemon.presentation.R

fun Fragment.navigate(
    fragment: Fragment,
    transaction: FragmentTransaction.() -> Unit = {}
) {
    activity?.supportFragmentManager?.commit {
        transaction()
        replace(R.id.content_container, fragment, fragment::class.java.canonicalName)
        addToBackStack(fragment::class.java.canonicalName)
    }
}