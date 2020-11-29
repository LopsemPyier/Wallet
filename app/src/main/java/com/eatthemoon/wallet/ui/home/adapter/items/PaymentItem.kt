package com.eatthemoon.wallet.ui.home.adapter.items

import com.eatthemoon.wallet.core.adapter.ViewBindingAdapterItem

interface PaymentItem: ViewBindingAdapterItem {
    companion object {
        const val PAYMENT_VIEW_TYPE = 1
    }
}