package com.eatthemoon.wallet.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.eatthemoon.wallet.R
import com.eatthemoon.wallet.core.viewBinding.toBigString
import com.eatthemoon.wallet.core.viewBinding.viewBinding
import com.eatthemoon.wallet.databinding.FragmentDetailBinding
import com.eatthemoon.wallet.db.entity.PaymentEntity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import reactivecircus.flowbinding.android.view.clicks

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val args by navArgs<DetailFragmentArgs>()

    private val binding by viewBinding(FragmentDetailBinding::bind)
    private val viewModel : DetailViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.paymentID = args.paymentID

        binding.deleteBtn
            .clicks()
            .map {
                DetailViewIntent.DeletePayment
            }
            .onEach {
                viewModel.processIntent(intent = it)
            }
            .launchIn(lifecycleScope)


        binding.editBtn
            .clicks()
            .onEach {
                findNavController().navigate(DetailFragmentDirections.editPayment(viewModel.paymentID ?: args.paymentID))
            }
            .launchIn(lifecycleScope)

        viewModel.payment.observe(viewLifecycleOwner) {
            updatePaymentText(it)
        }

        viewModel.uiEvent.observe(viewLifecycleOwner) {
            renderUiEvent(it)
        }
    }

    private fun renderUiEvent(detailUiEvent: DetailUiEvent) {
        when (detailUiEvent) {
            DetailUiEvent.GoBack ->
                findNavController().navigateUp()
        }
    }

    private fun updatePaymentText(paymentEntity: PaymentEntity) {
        binding.paymentAmountTv.text = getString(if (paymentEntity.category == PaymentEntity.INCOME) R.string.euro_format_plus else R.string.euro_format_minus, paymentEntity.amount)
        binding.paymentLocationTv.text = paymentEntity.location
        binding.paymentDateTv.text = paymentEntity.date.toBigString(binding.root.context)
        binding.paymentCategoryTv.text = getString(
                when (paymentEntity.category) {
                    PaymentEntity.GIFT -> R.string.gift
                    PaymentEntity.INCOME -> R.string.income
                    PaymentEntity.SHOPPING -> R.string.shopping
                    PaymentEntity.EATING -> R.string.eating
                    PaymentEntity.ENTERTAINMENT -> R.string.entertainment
                    PaymentEntity.OTHER -> R.string.other
                    else -> R.string.other
                }
        )
        binding.paymentCategoryTv.background = resources.getColor(
                when (paymentEntity.category) {
                    PaymentEntity.GIFT -> R.color.gift
                    PaymentEntity.INCOME -> R.color.income
                    PaymentEntity.SHOPPING -> R.color.shopping
                    PaymentEntity.EATING -> R.color.eating
                    PaymentEntity.ENTERTAINMENT -> R.color.entertainment
                    PaymentEntity.OTHER -> R.color.other
                    else -> R.color.other
                }
        ).toDrawable()

    }
}