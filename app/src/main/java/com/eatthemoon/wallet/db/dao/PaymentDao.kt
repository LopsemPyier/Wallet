package com.eatthemoon.wallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eatthemoon.wallet.db.entity.PAYMENT_TABLE_NAME
import com.eatthemoon.wallet.db.entity.PaymentEntity
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDateTime

@Dao
interface PaymentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(paymentEntity: PaymentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayments(paymentsEntity: List<PaymentEntity>)

    @Query("SELECT * FROM $PAYMENT_TABLE_NAME WHERE (date >= :from AND date <= :to) ORDER BY CASE WHEN :desc = 0 THEN amount END ASC, CASE WHEN :desc = 1 THEN amount END DESC LIMIT 100")
    fun getPaymentsByAmount(from: LocalDateTime, to: LocalDateTime, desc: Boolean) : Flow<List<PaymentEntity>>

    @Query("SELECT * FROM $PAYMENT_TABLE_NAME WHERE (date >= :from AND date <= :to) ORDER BY CASE WHEN :desc = 0 THEN date END ASC, CASE WHEN :desc = 1 THEN date END DESC LIMIT 100")
    fun getPaymentsByDate(from: LocalDateTime, to: LocalDateTime, desc: Boolean) : Flow<List<PaymentEntity>>

    @Query("SELECT * FROM $PAYMENT_TABLE_NAME WHERE ID = :ID")
    fun getPayment(ID: Int): Flow<PaymentEntity>

    @Query("DELETE FROM $PAYMENT_TABLE_NAME WHERE ID = :ID")
    suspend fun delete(ID: Int)
}