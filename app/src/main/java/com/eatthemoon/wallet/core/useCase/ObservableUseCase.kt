package com.eatthemoon.wallet.core.useCase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface ObservableUseCase<Type> {
    val dispatcher: CoroutineDispatcher
    fun observe() : Flow<Type>
}