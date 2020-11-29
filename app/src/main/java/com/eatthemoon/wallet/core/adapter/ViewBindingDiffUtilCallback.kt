package com.eatthemoon.wallet.core.adapter

import androidx.recyclerview.widget.DiffUtil

abstract class ViewBindingDiffUtilCallback<Item : ViewBindingAdapterItem> :
        DiffUtil.ItemCallback<Item>()