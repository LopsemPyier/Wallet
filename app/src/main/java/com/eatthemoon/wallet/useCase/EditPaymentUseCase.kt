package com.eatthemoon.wallet.useCase

import com.eatthemoon.wallet.core.dispatchers.AppCoroutineDispatchers
import com.eatthemoon.wallet.core.useCase.NoResultUseCase
import com.eatthemoon.wallet.db.entity.PaymentEntity
import com.eatthemoon.wallet.repository.CurrentRepo
import com.eatthemoon.wallet.repository.PaymentRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import org.threeten.bp.LocalDateTime


class EditPaymentUseCase(
        private val appCoroutineDispatchers: AppCoroutineDispatchers,
        private val paymentRepo: PaymentRepo,
        private val currentRepo: CurrentRepo
): NoResultUseCase<EditPaymentUseCase.Params>() {

    data class Params(
        val amount: Double,
        val location: String,
        val date: LocalDateTime,
        val category: Int,
        val oldCategory: Int,
        val ID: Int,
        val oldAmount: Double
    )

    override val workDispatcher: CoroutineDispatcher
        get() = appCoroutineDispatchers.io

    override suspend fun run(params: Params) {
        paymentRepo.insert(
                PaymentEntity(
                        params.amount,
                        params.location,
                        params.date,
                        params.category,
                        params.ID
                )
        )

        val delta =
                (if (params.oldCategory == PaymentEntity.INCOME) - params.oldAmount else + params.oldAmount) - (if (params.category == PaymentEntity.INCOME) - params.amount else + params.amount)


        val current = currentRepo.observeCurrent().first().apply {
            amount += delta
        }
        currentRepo.insert(
                current
        )
    }
}