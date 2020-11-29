package com.eatthemoon.wallet.ui.home.adapter.items

import org.threeten.bp.LocalDateTime

data class PaymentItemImpl(
    val amount: Double,
    val location: String,
    val date: LocalDateTime,
    val category: Int,
    val ID: Int
) : PaymentItem {
    override val itemViewType: Int
        get() = PaymentItem.PAYMENT_VIEW_TYPE
}