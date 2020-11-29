package com.eatthemoon.wallet.ui.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.eatthemoon.wallet.R
import com.eatthemoon.wallet.core.viewBinding.hide
import com.eatthemoon.wallet.core.viewBinding.show
import com.eatthemoon.wallet.core.viewBinding.viewBinding
import com.eatthemoon.wallet.databinding.FragmentNewBinding
import com.eatthemoon.wallet.db.entity.PaymentEntity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import reactivecircus.flowbinding.android.view.clicks

class EditFragment: Fragment(R.layout.fragment_new) {

    private val binding by viewBinding(FragmentNewBinding::bind)
    private val viewModel: EditViewModel by viewModel()

    private val args: EditFragmentArgs by navArgs()

    private var isChecking = true
    private var mCheckedId: Int = R.id.other_rb

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.ID = args.paymentID

        viewModel.payment.observe(viewLifecycleOwner) {
            it?.let {
                updateForm(it)
            }
        }

        binding.form.paymentSaveBtn
            .clicks()
            .map {
                EditViewIntent.EditPayment(
                    amount = binding.form.paymentAmountInsertEt.text.toString().toDoubleOrNull() ?: -1.0,
                    location = binding.form.paymentLocationInsertEt.text.toString(),
                    category = when (binding.form.categoryRg.checkedRadioButtonId) {
                        R.id.shopping_rb -> PaymentEntity.SHOPPING
                        R.id.eating_rb -> PaymentEntity.EATING
                        R.id.income_rb -> PaymentEntity.INCOME
                        R.id.entertainment_rb -> PaymentEntity.ENTERTAINMENT
                        R.id.gift_rb -> PaymentEntity.GIFT
                        R.id.other_rb -> PaymentEntity.OTHER
                        else -> PaymentEntity.OTHER
                    }
                )
            }
            .onEach {
                binding.form.paymentAmountErrorTv.hide()
                binding.form.paymentLocationErrorTv.hide()
                viewModel.processIntent(intent = it)
            }
            .launchIn(lifecycleScope)

        viewModel.uiEvent.observe(viewLifecycleOwner) {
            renderUiEvent(it)
        }

        binding.form.categoryRg.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                binding.form.categoryRg2.clearCheck()
                mCheckedId = checkedId
            }
            isChecking = true
        }

        binding.form.categoryRg2.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                binding.form.categoryRg.clearCheck()
                mCheckedId = checkedId
            }
            isChecking = true
        }
    }

    private fun renderUiEvent(editUiEvent: EditUiEvent) {
        when (editUiEvent) {
            EditUiEvent.PaymentSavedGoBackHome ->
                findNavController().navigateUp()
            EditUiEvent.ErrorInvalidAmount -> {
                binding.form.paymentAmountErrorTv.text = getString(R.string.current_amount_error_invalid_amount)
                binding.form.paymentAmountErrorTv.show()
            }
            EditUiEvent.ErrorInvalidLocation -> {
                binding.form.paymentLocationErrorTv.text = getString(R.string.payment_location_error_invalid_location)
                binding.form.paymentLocationErrorTv.show()
            }
        }
    }

    private fun updateForm(paymentEntity: PaymentEntity) {
        binding.form.paymentAmountInsertEt.setText(paymentEntity.amount.toString())
        binding.form.paymentLocationInsertEt.setText(paymentEntity.location)
        when (paymentEntity.category) {
            PaymentEntity.SHOPPING -> binding.form.shoppingRb.isChecked = true
            PaymentEntity.EATING -> binding.form.eatingRb.isChecked = true
            PaymentEntity.ENTERTAINMENT -> binding.form.entertainmentRb.isChecked = true
            PaymentEntity.GIFT -> binding.form.giftRb.isChecked = true
            PaymentEntity.INCOME -> binding.form.incomeRb.isChecked = true
            PaymentEntity.OTHER -> binding.form.otherRb.isChecked = true
            else -> binding.form.otherRb.isChecked = true
        }
    }
}