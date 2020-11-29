package com.eatthemoon.wallet.useCase

import com.eatthemoon.wallet.core.dispatchers.AppCoroutineDispatchers
import com.eatthemoon.wallet.core.useCase.FlowUseCase
import com.eatthemoon.wallet.db.dao.PaymentDao
import com.eatthemoon.wallet.db.entity.PaymentEntity
import com.eatthemoon.wallet.repository.PaymentRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class ObservePaymentUseCase(
        private val appCoroutineDispatchers: AppCoroutineDispatchers,
        private val paymentRepo: PaymentRepo
): FlowUseCase<PaymentEntity, Int>() {
    override fun doWork(params: Int): Flow<PaymentEntity> =
            paymentRepo.observePayment(ID = params)

    override val dispatcher: CoroutineDispatcher
        get() = appCoroutineDispatchers.io
}