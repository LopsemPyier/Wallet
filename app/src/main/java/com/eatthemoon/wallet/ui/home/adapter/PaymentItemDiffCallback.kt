package com.eatthemoon.wallet.ui.home.adapter

import android.annotation.SuppressLint
import com.eatthemoon.wallet.core.adapter.ViewBindingDiffUtilCallback
import com.eatthemoon.wallet.ui.home.adapter.items.PaymentItem
import com.eatthemoon.wallet.ui.home.adapter.items.PaymentItemImpl

object PaymentItemDiffCallback : ViewBindingDiffUtilCallback<PaymentItem>() {
    override fun areItemsTheSame(oldItem: PaymentItem, newItem: PaymentItem): Boolean {
        if (oldItem !is PaymentItemImpl || newItem !is PaymentItemImpl) {
            return false
        }

        return oldItem.ID == newItem.ID
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: PaymentItem, newItem: PaymentItem): Boolean {
        if (oldItem !is PaymentItemImpl || newItem !is PaymentItemImpl) {
            return false
        }

        return oldItem == newItem
    }

}