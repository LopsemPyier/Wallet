package com.eatthemoon.wallet.di

import com.eatthemoon.wallet.core.dispatchers.AppCoroutineDispatchers
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import kotlin.math.sin

val dispatchersModule = module {
    single {
        AppCoroutineDispatchers(
            io = Dispatchers.IO,
            computation = Dispatchers.Default,
            main = Dispatchers.Main
        )
    }
}