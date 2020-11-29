package com.eatthemoon.wallet.repository

import com.eatthemoon.wallet.db.entity.PaymentEntity
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDateTime

interface PaymentRepo {
    suspend fun insert(paymentEntity: PaymentEntity)
    suspend fun insert(paymentsEntity: List<PaymentEntity>)
    fun observePayments(sortBy: String, from: LocalDateTime, to: LocalDateTime, desc : Boolean = false): Flow<List<PaymentEntity>>
    fun observePayment(ID: Int): Flow<PaymentEntity>
    suspend fun delete(ID: Int)
}