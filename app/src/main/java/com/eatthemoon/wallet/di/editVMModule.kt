package com.eatthemoon.wallet.di

import com.eatthemoon.wallet.ui.edit.EditViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val editVMModule = module {
    viewModel {
        EditViewModel(
                editPaymentUseCase = get(),
                observePaymentUseCase = get()
        )
    }
}