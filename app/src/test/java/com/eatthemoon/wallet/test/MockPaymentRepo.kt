package com.eatthemoon.wallet.test

import com.eatthemoon.wallet.db.entity.PaymentEntity
import com.eatthemoon.wallet.repository.PaymentRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.threeten.bp.LocalDateTime

class MockPaymentRepo(
    private val observedPayments: List<PaymentEntity>
): PaymentRepo {

    val addPaymentRender = RenderRecorder<PaymentEntity>()
    val updatePaymentRender = RenderRecorder<List<PaymentEntity>>()
    override suspend fun insert(paymentEntity: PaymentEntity) =
        addPaymentRender.add(paymentEntity)

    override suspend fun insert(paymentsEntity: List<PaymentEntity>) =
        updatePaymentRender.add(paymentsEntity)

    override fun observePayments(
        sortBy: String,
        from: LocalDateTime,
        to: LocalDateTime,
        desc: Boolean
    ): Flow<List<PaymentEntity>> = flowOf(observedPayments)

    override fun observePayment(ID: Int): Flow<PaymentEntity> = flowOf(observedPayments.filter { it.ID == ID }[0])

    override suspend fun delete(ID: Int) {

    }

    fun reset() {
        addPaymentRender.reset()
    }
}