package com.eatthemoon.wallet.ui.edit

import com.eatthemoon.wallet.core.viewModel.ViewIntent

sealed class EditViewIntent: ViewIntent {
    data class EditPayment(val amount: Double, val location: String, val category: Int) : EditViewIntent()
}