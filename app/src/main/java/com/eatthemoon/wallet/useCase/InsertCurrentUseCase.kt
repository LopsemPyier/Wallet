package com.eatthemoon.wallet.useCase

import com.eatthemoon.wallet.core.dispatchers.AppCoroutineDispatchers
import com.eatthemoon.wallet.core.useCase.NoResultUseCase
import com.eatthemoon.wallet.db.entity.CurrentEntity
import com.eatthemoon.wallet.repository.CurrentRepo
import kotlinx.coroutines.CoroutineDispatcher

class InsertCurrentUseCase(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val currentRepo: CurrentRepo
) : NoResultUseCase<InsertCurrentUseCase.Params>() {

    data class Params(
        val amount: Double
    )

    override val workDispatcher: CoroutineDispatcher
        get() = appCoroutineDispatchers.io

    override suspend fun run(params: Params) {
        currentRepo.insert(
            currentEntity = CurrentEntity(
                amount = params.amount
            )
        )
    }
}