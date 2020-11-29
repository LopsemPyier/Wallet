package com.eatthemoon.wallet.useCase

import com.eatthemoon.wallet.core.dispatchers.AppCoroutineDispatchers
import com.eatthemoon.wallet.core.useCase.FlowUseCase
import com.eatthemoon.wallet.repository.CurrentRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class ObserveIfCurrentInsertedUseCase(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val currentRepo: CurrentRepo
): FlowUseCase<Boolean, Unit>() {

    override fun doWork(params: Unit): Flow<Boolean> =
        currentRepo.observeIfCurrentInserted()

    override val dispatcher: CoroutineDispatcher
        get() = appCoroutineDispatchers.io
}