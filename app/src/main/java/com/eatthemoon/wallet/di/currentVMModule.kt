package com.eatthemoon.wallet.di

import com.eatthemoon.wallet.ui.current.CurrentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val currentVMModule = module {
    viewModel {
        CurrentViewModel(
            insertCurrentUseCase = get()
        )
    }
}