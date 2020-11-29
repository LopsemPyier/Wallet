package com.eatthemoon.wallet.ui.home

import com.eatthemoon.wallet.core.viewModel.ViewIntent

sealed class HomeViewIntent: ViewIntent {
    data class PaymentListItemClicked(val ID: Int): HomeViewIntent()
}