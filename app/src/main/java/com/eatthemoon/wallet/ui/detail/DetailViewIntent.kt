package com.eatthemoon.wallet.ui.detail

import com.eatthemoon.wallet.core.viewModel.ViewIntent

sealed class DetailViewIntent : ViewIntent {
    object DeletePayment : DetailViewIntent()
}