package com.eatthemoon.wallet.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.eatthemoon.wallet.core.viewModel.BaseViewModel
import com.eatthemoon.wallet.db.entity.PaymentEntity
import com.eatthemoon.wallet.useCase.DeletePaymentUseCase
import com.eatthemoon.wallet.useCase.ObservePaymentUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class DetailViewModel(
        private val observePaymentUseCase: ObservePaymentUseCase,
        private val deletePaymentUseCase: DeletePaymentUseCase
): BaseViewModel<DetailViewIntent, DetailUiEvent>() {

    var paymentID: Int? = null
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

    override suspend fun onViewIntent(viewIntent: DetailViewIntent) {
        when (viewIntent) {
            is DetailViewIntent.DeletePayment -> deletePayment()
        }
    }

    private suspend fun deletePayment() {
        deletePaymentUseCase(
                params = DeletePaymentUseCase.Params(
                        paymentID!!
                )
        )

        addUiEvent(DetailUiEvent.GoBack)
    }

}