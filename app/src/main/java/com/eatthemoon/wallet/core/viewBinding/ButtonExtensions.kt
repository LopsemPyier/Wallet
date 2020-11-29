package com.eatthemoon.wallet.core.viewBinding

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks

fun View.bindToDestination(fragment: Fragment, direction: NavDirections) {
    this
            .clicks()
            .onEach {
                fragment.findNavController().navigate(direction)
            }
            .launchIn(fragment.lifecycleScope)
}