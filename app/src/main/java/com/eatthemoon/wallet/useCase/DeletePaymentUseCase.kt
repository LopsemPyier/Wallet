package com.eatthemoon.wallet.useCase

import androidx.room.Delete
import com.eatthemoon.wallet.core.dispatchers.AppCoroutineDispatchers
import com.eatthemoon.wallet.core.useCase.NoResultUseCase
import com.eatthemoon.wallet.repository.PaymentRepo
import kotlinx.coroutines.CoroutineDispatcher

class DeletePaymentUseCase(
        private val appCoroutineDispatchers: AppCoroutineDispatchers,
        private val paymentRepo: PaymentRepo
): NoResultUseCase<DeletePaymentUseCase.Params>() {

    data class Params(
            val ID: Int
    )

    override val workDispatcher: CoroutineDispatcher
        get() = appCoroutineDispatchers.io

    override suspend fun run(params: Params) {
        paymentRepo.delete(params.ID)
    }


}