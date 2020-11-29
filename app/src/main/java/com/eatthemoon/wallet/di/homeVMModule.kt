package com.eatthemoon.wallet.di

import com.eatthemoon.wallet.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeVMModule = module {
    viewModel {
        HomeViewModel(
                observeCurrentUseCase = get(),
                observeIfCurrentInsertedUseCase = get(),
                observePaymentsUseCase = get()
        )
    }
}