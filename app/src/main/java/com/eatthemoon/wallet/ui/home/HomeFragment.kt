package com.eatthemoon.wallet.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.eatthemoon.wallet.R
import com.eatthemoon.wallet.core.viewBinding.bindToDestination
import com.eatthemoon.wallet.core.viewBinding.hide
import com.eatthemoon.wallet.core.viewBinding.show
import com.eatthemoon.wallet.core.viewBinding.viewBinding
import com.eatthemoon.wallet.databinding.FragmentHomeBinding
import com.eatthemoon.wallet.ui.home.adapter.PaymentAdapter
import com.eatthemoon.wallet.ui.home.adapter.items.PaymentItemImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModel()
    private val adapter = PaymentAdapter { ID ->
        goToDetailFragment(ID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.uiEvent.observe(viewLifecycleOwner) {
            renderUiEvent(it)
        }

        binding.paymentsRv.adapter = adapter

        binding.seeMoreBtn
            .bindToDestination(
                this, HomeFragmentDirections.showMore())

        binding.newPaymentBtn
            .bindToDestination(
                this, HomeFragmentDirections.addPayment())

        viewModel.current.observe(viewLifecycleOwner) {
            it?.let {
                updateCurrentText(it.amount)
            }
        }

        viewModel.payments.observe(viewLifecycleOwner) {
            displayPayment(it)
        }
    }

    private fun renderUiEvent(homeUiEvent: HomeUiEvent) {
        when (homeUiEvent) {
            HomeUiEvent.GoInsertCurrent -> {
                    findNavController().navigate(HomeFragmentDirections.showFirstTimeOpen())
                }

            HomeUiEvent.DataLoaded -> {
//                binding.loadingPb.visibility = View.GONE
            }
            HomeUiEvent.DataLoading -> {
//                binding.loadingPb.visibility = View.VISIBLE
            }
        }
    }

    private fun updateCurrentText(amount : Double) {
        binding.currentAmountTv.text = getString(R.string.euro_format, amount)
    }

    private fun displayPayment(payments: List<PaymentItemImpl>) {
        adapter.submitList(payments)
        if (payments.isEmpty()) {
            binding.paymentsRv.hide()
            binding.noPaymentLabelTv.show()
        } else {
            binding.paymentsRv.show()
            binding.noPaymentLabelTv.hide()
        }
    }

    private fun goToDetailFragment(ID: Int) {
        findNavController().navigate(HomeFragmentDirections.showDetail(ID))
    }
}