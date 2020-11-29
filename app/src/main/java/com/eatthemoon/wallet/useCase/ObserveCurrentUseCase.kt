package com.eatthemoon.wallet.useCase

import com.eatthemoon.wallet.core.dispatchers.AppCoroutineDispatchers
import com.eatthemoon.wallet.core.useCase.FlowUseCase
import com.eatthemoon.wallet.db.entity.CurrentEntity
import com.eatthemoon.wallet.repository.CurrentRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class ObserveCurrentUseCase(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val currentRepo: CurrentRepo
) : FlowUseCase<CurrentEntity, Unit>() {

    override fun doWork(params: Unit): Flow<CurrentEntity> =
        currentRepo.observeCurrent()

    override val dispatcher: CoroutineDispatcher
        get() = appCoroutineDispatchers.io
}