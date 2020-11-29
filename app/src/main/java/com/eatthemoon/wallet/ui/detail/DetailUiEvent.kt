package com.eatthemoon.wallet.ui.detail

import com.eatthemoon.wallet.core.viewModel.UiEvent

sealed class DetailUiEvent : UiEvent {
    object GoBack: DetailUiEvent()
}