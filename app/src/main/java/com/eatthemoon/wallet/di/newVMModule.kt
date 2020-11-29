package com.eatthemoon.wallet.di

import com.eatthemoon.wallet.ui.newPayment.NewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val newVMModule = module {
    viewModel {
        NewViewModel(
                insertPaymentUseCase = get()
        )
    }
}