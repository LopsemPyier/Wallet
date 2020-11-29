package com.eatthemoon.wallet.ui.edit

import com.eatthemoon.wallet.core.viewModel.UiEvent

sealed class EditUiEvent: UiEvent {
    object PaymentSavedGoBackHome : EditUiEvent()
    object ErrorInvalidAmount : EditUiEvent()
    object ErrorInvalidLocation : EditUiEvent()
}