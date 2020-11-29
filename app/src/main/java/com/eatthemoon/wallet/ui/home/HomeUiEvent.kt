package com.eatthemoon.wallet.ui.home

import com.eatthemoon.wallet.core.viewModel.UiEvent

sealed class HomeUiEvent: UiEvent {
    object GoInsertCurrent : HomeUiEvent()
    object DataLoading: HomeUiEvent()
    object DataLoaded: HomeUiEvent()
}