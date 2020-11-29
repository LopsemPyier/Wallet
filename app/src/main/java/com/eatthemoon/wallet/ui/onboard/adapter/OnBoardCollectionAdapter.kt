package com.eatthemoon.wallet.ui.onboard.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.eatthemoon.wallet.ui.onboard.fragments.OnBoardDataFragment
import com.eatthemoon.wallet.ui.onboard.fragments.OnBoardExpensesFragment
import com.eatthemoon.wallet.ui.onboard.fragments.OnBoardStatFragment

class OnBoardCollectionAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> OnBoardExpensesFragment()
        1 -> OnBoardDataFragment()
        2 -> OnBoardStatFragment()
        else -> OnBoardExpensesFragment()
    }
}