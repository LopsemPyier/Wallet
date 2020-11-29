package com.eatthemoon.wallet.ui.current

import com.eatthemoon.wallet.core.viewModel.UiEvent

sealed class CurrentUiEvent: UiEvent {
    object CurrentInserted: CurrentUiEvent()
    object ErrorInvalidAmount: CurrentUiEvent()
}