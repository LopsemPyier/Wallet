package com.eatthemoon.wallet.ui.current

import com.eatthemoon.wallet.core.viewModel.ViewIntent

sealed class CurrentViewIntent: ViewIntent {
    data class InsertCurrent(val amount: Double) : CurrentViewIntent()
}