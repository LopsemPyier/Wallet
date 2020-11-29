package com.eatthemoon.wallet.ui.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.eatthemoon.wallet.core.viewModel.BaseViewModel
import com.eatthemoon.wallet.db.entity.PaymentEntity
import com.eatthemoon.wallet.useCase.EditPaymentUseCase
import com.eatthemoon.wallet.useCase.ObservePaymentUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class EditViewModel(
        private val editPaymentUseCase: EditPaymentUseCase,
        private val observePaymentUseCase: ObservePaymentUseCase
) : BaseViewModel<EditViewIntent, EditUiEvent>() {

    var ID: Int? = null
        set(value) {
            if (value == field) return
            field = value
            observePaymentUseCase(field!!)
        }

    private val _payment = observePaymentUseCase
            .observe()
            .asLiveData(viewModelScope.coroutineContext)
    val payment: LiveData<PaymentEntity>
        get() = _payment

    override suspend fun onViewIntent(viewIntent: EditViewIntent) {
        when (viewIntent) {
            is EditViewIntent.EditPayment -> editPayment(payment = viewIntent)
        }
    }

    private suspend fun editPayment(payment: EditViewIntent.EditPayment) {
        if (payment.amount < 0) {
            addUiEvent(EditUiEvent.ErrorInvalidAmount)
            return
        }

        if (payment.location.isEmpty() || payment.location.isBlank()) {
            addUiEvent(EditUiEvent.ErrorInvalidLocation)
            return
        }

        val oldPayment = this.payment.value!!

        editPaymentUseCase(
                EditPaymentUseCase.Params(
                        amount = payment.amount,
                        location = payment.location,
                        oldPayment.date,
                        payment.category,
                        oldPayment.category,
                        oldPayment.ID!!,
                        oldPayment.amount
                )
        )

        addUiEvent(EditUiEvent.PaymentSavedGoBackHome)
    }
}