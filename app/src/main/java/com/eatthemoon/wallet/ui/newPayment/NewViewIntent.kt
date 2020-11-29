package com.eatthemoon.wallet.ui.newPayment

import com.eatthemoon.wallet.core.viewModel.ViewIntent

sealed class NewViewIntent: ViewIntent {
    data class InsertPayment(val amount: Double, val location: String, val category: Int) : NewViewIntent()
}