package com.eatthemoon.wallet.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.eatthemoon.wallet.core.db.Converters
import com.eatthemoon.wallet.db.dao.CurrentDao
import com.eatthemoon.wallet.db.dao.PaymentDao
import com.eatthemoon.wallet.db.entity.CurrentEntity
import com.eatthemoon.wallet.db.entity.PaymentEntity

@Database(
    entities = [CurrentEntity::class, PaymentEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class WalletDB: RoomDatabase() {
    companion object {
        const val DB_NAME = "wallet_app.db"
    }

    abstract fun currentDao() : CurrentDao
    abstract fun paymentDao() : PaymentDao
}