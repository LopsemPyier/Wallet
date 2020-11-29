package com.eatthemoon.wallet.ui.current

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.eatthemoon.wallet.R
import com.eatthemoon.wallet.core.viewBinding.hide
import com.eatthemoon.wallet.core.viewBinding.show
import com.eatthemoon.wallet.core.viewBinding.viewBinding
import com.eatthemoon.wallet.databinding.FragmentCurrentBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import reactivecircus.flowbinding.android.view.clicks


@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class CurrentFragment : Fragment(R.layout.fragment_current) {

    private val binding by viewBinding(FragmentCurrentBinding::bind)
    private val viewModel: CurrentViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.currentSaveBtn
            .clicks()
            .map {
                CurrentViewIntent.InsertCurrent(
                    amount = binding.currentAmountInsertEt.text.toString().toDoubleOrNull() ?: -1.0
                )
            }
            .onEach {
                binding.currentAmountErrorTv.hide()
                viewModel.processIntent(intent = it)
            }
            .launchIn(lifecycleScope)

        viewModel.uiEvent.observe(viewLifecycleOwner, {
            renderUiEvent(it)
        })
    }

    private fun renderUiEvent(currentUiEvent: CurrentUiEvent) {
        when (currentUiEvent) {
            CurrentUiEvent.CurrentInserted ->
                findNavController().navigate(CurrentFragmentDirections.backHomeAfterFirstOpen())
            CurrentUiEvent.ErrorInvalidAmount -> {
                binding.currentAmountErrorTv.text = getString(R.string.current_amount_error_invalid_amount)
                binding.currentAmountErrorTv.show()
            }
        }
    }
}