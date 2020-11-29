package com.eatthemoon.wallet.di

import com.eatthemoon.wallet.ui.listPayments.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val listVMModule = module {
    viewModel {
        ListViewModel(
                observePaymentsUseCase = get()
        )
    }
}