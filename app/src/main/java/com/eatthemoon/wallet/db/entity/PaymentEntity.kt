package com.eatthemoon.wallet.db.entity

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eatthemoon.wallet.R
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import org.threeten.bp.temporal.ChronoField
import java.util.*

const val PAYMENT_TABLE_NAME = "payment_table"

@Entity(tableName = PAYMENT_TABLE_NAME)
data class PaymentEntity(
    var amount: Double,
    var location: String,
    var date: LocalDateTime,
    var category: Int,
    @PrimaryKey(autoGenerate = true)
    var ID: Int? = null
) {
    companion object {
        const val SHOPPING = 1
        const val EATING = 2
        const val ENTERTAINMENT = 3
        const val GIFT = 4
        const val INCOME = 5
        const val OTHER = 6
    }
}