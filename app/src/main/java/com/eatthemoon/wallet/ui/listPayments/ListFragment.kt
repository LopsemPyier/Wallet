package com.eatthemoon.wallet.ui.listPayments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.eatthemoon.wallet.R
import com.eatthemoon.wallet.core.viewBinding.hide
import com.eatthemoon.wallet.core.viewBinding.show
import com.eatthemoon.wallet.core.viewBinding.viewBinding
import com.eatthemoon.wallet.databinding.FragmentListBinding
import com.eatthemoon.wallet.ui.home.adapter.PaymentAdapter
import com.eatthemoon.wallet.ui.home.adapter.items.PaymentItemImpl
import com.eatthemoon.wallet.useCase.PaymentLimitType
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import reactivecircus.flowbinding.android.view.clicks
import reactivecircus.flowbinding.android.widget.itemClicks


class ListFragment : Fragment(R.layout.fragment_list) {

    private val binding by viewBinding(FragmentListBinding::bind)
    private val viewModel: ListViewModel by viewModel()
    private val adapter = PaymentAdapter { ID ->
        goToDetailFragment(ID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.paymentsRv.adapter = adapter

        binding.amountViewBtn
            .clicks()
            .map {
                ListViewIntent.SortByAmount
            }
            .onEach {
                viewModel.processIntent(it)
                binding.amountViewBtn.imageTintList = AppCompatResources.getColorStateList(
                    requireContext(),
                    R.color.primary
                )
                binding.dateViewBtn.imageTintList = null
            }
            .launchIn(lifecycleScope)

        binding.limitSp
                .onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                lifecycleScope.launch {
                    viewModel.processIntent(ListViewIntent.Limit(when (position) {
                        0 -> PaymentLimitType.ThisWeek
                        1 -> PaymentLimitType.LastWeek
                        2 -> PaymentLimitType.ThisMonth
                        3 -> PaymentLimitType.LastMonth
                        4 -> PaymentLimitType.ThisYear
                        5 -> PaymentLimitType.NoLimit
                        else -> PaymentLimitType.ThisMonth
                    }))
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                lifecycleScope.launch {
                    viewModel.processIntent(ListViewIntent.Limit(
                            PaymentLimitType.ThisMonth
                    ))
                }

            }

        }

        binding.dateViewBtn
            .clicks()
            .map {
                ListViewIntent.SortByDate
            }
            .onEach {
                viewModel.processIntent(it)
                binding.dateViewBtn.imageTintList = AppCompatResources.getColorStateList(
                    requireContext(),
                    R.color.primary
                )
                binding.amountViewBtn.imageTintList = null
            }
            .launchIn(lifecycleScope)

        viewModel.payments.observe(viewLifecycleOwner) {
            displayPayment(it)
        }
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
        findNavController().navigate(ListFragmentDirections.showDetailFromList(ID))
    }


}