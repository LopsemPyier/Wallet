package com.eatthemoon.wallet.ui.onboard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.eatthemoon.wallet.R
import com.eatthemoon.wallet.core.viewBinding.bindToDestination
import com.eatthemoon.wallet.core.viewBinding.viewBinding
import com.eatthemoon.wallet.databinding.FragmentOnboardBinding
import com.eatthemoon.wallet.ui.onboard.adapter.OnBoardCollectionAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks
import reactivecircus.flowbinding.viewpager2.pageSelections

class OnBoardFragment : Fragment(R.layout.fragment_onboard) {
    private lateinit var adapter: OnBoardCollectionAdapter
    private lateinit var callback: OnBackPressedCallback

    private val binding by viewBinding(FragmentOnboardBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (binding.viewPager.currentItem > 0)
                binding.viewPager.setCurrentItem(binding.viewPager.currentItem - 1 , true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = OnBoardCollectionAdapter(this)

        binding.viewPager.adapter = adapter

        binding.button
            .clicks()
            .onEach {
                if (binding.viewPager.currentItem < adapter.itemCount - 1)
                    binding.viewPager.setCurrentItem(binding.viewPager.currentItem + 1 , true)
                else
                    findNavController().navigate(OnBoardFragmentDirections.insertCurrent())
            }
            .launchIn(lifecycleScope)

        binding.viewPager
            .pageSelections()
            .skipInitialValue()
            .onEach { position ->
                updateButtons(position)
            }
            .launchIn(lifecycleScope)

        binding.textView
            .bindToDestination(
                this,
                OnBoardFragmentDirections.insertCurrent()
            )

    }

    private fun updateButtons(position: Int) {
        when (position) {
            0 -> {
                callback.isEnabled = false
                binding.textView.visibility = View.VISIBLE
                binding.button.text = getString(R.string.next)
            }
            1 -> {
                callback.isEnabled = true
                binding.textView.visibility = View.VISIBLE
                binding.button.text = getString(R.string.next)
            }
            2 -> {
                callback.isEnabled = true
                binding.textView.visibility = View.GONE
                binding.button.text = getString(R.string.start)
            }
        }
    }
}