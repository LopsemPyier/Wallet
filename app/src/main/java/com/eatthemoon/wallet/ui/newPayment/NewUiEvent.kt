package com.eatthemoon.wallet.ui.newPayment

import com.eatthemoon.wallet.core.viewModel.UiEvent

sealed class NewUiEvent: UiEvent {
    object PaymentSavedGoBackHome : NewUiEvent()
    object ErrorInvalidAmount : NewUiEvent()
    object ErrorInvalidLocation : NewUiEvent()
}