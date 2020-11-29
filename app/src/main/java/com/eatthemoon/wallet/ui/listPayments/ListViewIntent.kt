package com.eatthemoon.wallet.ui.listPayments

import com.eatthemoon.wallet.core.viewModel.ViewIntent
import com.eatthemoon.wallet.useCase.PaymentLimitType

sealed class ListViewIntent : ViewIntent {
    object SortByAmount: ListViewIntent()
    object SortByDate: ListViewIntent()
    data class Limit(val limit: PaymentLimitType): ListViewIntent()
}