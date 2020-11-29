package com.eatthemoon.wallet.di

import com.eatthemoon.wallet.useCase.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.dsl.module

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
val useCaseModule = module {
    factory {
        InsertCurrentUseCase(
            appCoroutineDispatchers = get(),
            currentRepo = get()
        )
    }

    factory {
        ObserveCurrentUseCase(
            appCoroutineDispatchers = get(),
            currentRepo = get()
        )
    }

    factory {
        ObserveIfCurrentInsertedUseCase(
            appCoroutineDispatchers = get(),
            currentRepo = get()
        )
    }

    factory {
        InsertPaymentUseCase(
                appCoroutineDispatchers = get(),
                paymentRepo = get(),
                currentRepo = get()
        )
    }

    factory {
        ObservePaymentsUseCase(
                appCoroutineDispatchers = get(),
                paymentRepo = get()
        )
    }

    factory {
        ObservePaymentUseCase(
                appCoroutineDispatchers = get(),
                paymentRepo = get()
        )
    }

    factory {
        DeletePaymentUseCase(
                appCoroutineDispatchers = get(),
                paymentRepo = get()
        )
    }

    factory {
        EditPaymentUseCase(
                appCoroutineDispatchers = get(),
                paymentRepo = get(),
                currentRepo = get()
        )
    }
}