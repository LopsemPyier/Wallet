package com.eatthemoon.wallet.ui.newPayment

import com.eatthemoon.wallet.core.viewModel.BaseViewModel
import com.eatthemoon.wallet.useCase.InsertPaymentUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class NewViewModel(
        private val insertPaymentUseCase: InsertPaymentUseCase
) : BaseViewModel<NewViewIntent, NewUiEvent>() {

    override suspend fun onViewIntent(viewIntent: NewViewIntent) {
        when (viewIntent) {
            is NewViewIntent.InsertPayment -> insertPayment(payment = viewIntent)
        }
    }

    private suspend fun insertPayment(payment: NewViewIntent.InsertPayment) {
        if (payment.amount < 0) {
            addUiEvent(NewUiEvent.ErrorInvalidAmount)
            return
        }

        if (payment.location.isEmpty() || payment.location.isBlank()) {
            addUiEvent(NewUiEvent.ErrorInvalidLocation)
            return
        }

        insertPaymentUseCase(
                InsertPaymentUseCase.Params(
                        amount = payment.amount,
                        location = payment.location,
                        category = payment.category
                )
        )

        addUiEvent(NewUiEvent.PaymentSavedGoBackHome)
    }
}