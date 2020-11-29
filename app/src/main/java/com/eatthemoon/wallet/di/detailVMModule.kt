package com.eatthemoon.wallet.di

import com.eatthemoon.wallet.ui.detail.DetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailVMModule = module {
    viewModel {
        DetailViewModel(
                observePaymentUseCase = get(),
                deletePaymentUseCase = get()
        )
    }
}