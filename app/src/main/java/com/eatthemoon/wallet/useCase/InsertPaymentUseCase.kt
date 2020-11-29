package com.eatthemoon.wallet.useCase

import com.eatthemoon.wallet.core.dispatchers.AppCoroutineDispatchers
import com.eatthemoon.wallet.core.useCase.NoResultUseCase
import com.eatthemoon.wallet.db.entity.PaymentEntity
import com.eatthemoon.wallet.repository.CurrentRepo
import com.eatthemoon.wallet.repository.PaymentRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import org.threeten.bp.LocalDateTime


class InsertPaymentUseCase(
        private val appCoroutineDispatchers: AppCoroutineDispatchers,
        private val paymentRepo: PaymentRepo,
        private val currentRepo: CurrentRepo
): NoResultUseCase<InsertPaymentUseCase.Params>() {

    data class Params(
            val amount: Double,
            val location: String,
            val category: Int
    )

    override val workDispatcher: CoroutineDispatcher
        get() = appCoroutineDispatchers.io

    override suspend fun run(params: Params) {
        paymentRepo.insert(
                PaymentEntity(
                        params.amount,
                        params.location,
                        LocalDateTime.now(),
                        params.category
                )
        )

        val current = currentRepo.observeCurrent().first().apply {
            amount += if (params.category == PaymentEntity.INCOME) params.amount else - params.amount
        }
        currentRepo.insert(
            current
        )
    }
}