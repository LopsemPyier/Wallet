package com.eatthemoon.wallet.repository

import com.eatthemoon.wallet.db.dao.PaymentDao
import com.eatthemoon.wallet.db.entity.PaymentEntity
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

class PaymentRepoImpl(
        private val paymentDao: PaymentDao
) : PaymentRepo {
    override suspend fun insert(paymentEntity: PaymentEntity) =
            paymentDao.insert(paymentEntity)

    override suspend fun insert(paymentsEntity: List<PaymentEntity>) =
            paymentDao.insertPayments(paymentsEntity)

    override fun observePayments(sortBy: String, from: LocalDateTime, to: LocalDateTime, desc: Boolean): Flow<List<PaymentEntity>> =
            if (sortBy == "amount")
                paymentDao.getPaymentsByAmount(from, to, desc)
            else
                paymentDao.getPaymentsByDate(from, to, desc)

    override fun observePayment(ID: Int): Flow<PaymentEntity> =
            paymentDao.getPayment(ID)

    override suspend fun delete(ID: Int) =
            paymentDao.delete(ID)
}