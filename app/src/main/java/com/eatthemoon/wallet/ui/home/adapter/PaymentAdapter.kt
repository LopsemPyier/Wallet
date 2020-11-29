package com.eatthemoon.wallet.ui.home.adapter

import android.os.Build
import android.util.Log
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.toColor
import com.eatthemoon.wallet.R
import com.eatthemoon.wallet.core.adapter.ViewBindingAdapter
import com.eatthemoon.wallet.core.adapter.ViewBindingViewHolder
import com.eatthemoon.wallet.core.viewBinding.toSmallString
import com.eatthemoon.wallet.databinding.ItemPaymentBinding
import com.eatthemoon.wallet.db.entity.PaymentEntity
import com.eatthemoon.wallet.ui.home.adapter.items.PaymentItem
import com.eatthemoon.wallet.ui.home.adapter.items.PaymentItemImpl

class PaymentAdapter(
    private val clickCallback: (ID: Int) -> Unit
) :
    ViewBindingAdapter<PaymentItem, ItemPaymentBinding>(PaymentItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBindingViewHolder<PaymentItem, ItemPaymentBinding> {
        val inflater = parent.layoutInflater

        return when(viewType) {
            PaymentItem.PAYMENT_VIEW_TYPE -> {
                val binding = ItemPaymentBinding.inflate(inflater, parent, false)
                WalletPaymentViewHolder(binding, clickCallback)
            }
            else -> throw IllegalStateException("Unknown viewType: $viewType")
        }
    }

    inner class WalletPaymentViewHolder(
        binding: ItemPaymentBinding,
        private val clickCallback : (ID: Int) -> Unit
    ): ViewBindingViewHolder<PaymentItem, ItemPaymentBinding>(binding) {
        @RequiresApi(Build.VERSION_CODES.M)
        override fun bind(item: PaymentItem) {
            item as PaymentItemImpl

            binding.paymentAmountTv.text =  binding.root.context.getString(if (item.category == PaymentEntity.INCOME) R.string.euro_format_plus else R.string.euro_format_minus, item.amount)
            binding.paymentAmountTv.setTextColor(binding.root.context.getColor(if (item.category == PaymentEntity.INCOME) R.color.income else R.color.outcome))
            binding.paymentLocationTv.text = item.location
            binding.paymentDateTv.text = item.date.toSmallString(binding.root.context)
            binding.root.setOnClickListener {
                clickCallback(item.ID)
            }
            binding.linearLayout.backgroundTintList = getColorStateList(binding.root.context,
                    when (item.category) {
                        PaymentEntity.GIFT -> R.color.gift
                        PaymentEntity.INCOME -> R.color.income
                        PaymentEntity.SHOPPING -> R.color.shopping
                        PaymentEntity.EATING -> R.color.eating
                        PaymentEntity.ENTERTAINMENT -> R.color.entertainment
                        PaymentEntity.OTHER -> R.color.other
                        else -> R.color.other
                    }
            )
        }
    }

}