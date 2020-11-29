package com.eatthemoon.wallet.di

import androidx.room.Room
import com.eatthemoon.wallet.core.dispatchers.AppCoroutineDispatchers
import com.eatthemoon.wallet.db.WalletDB
import com.eatthemoon.wallet.repository.CurrentRepo
import com.eatthemoon.wallet.repository.CurrentRepoImpl
import com.eatthemoon.wallet.repository.PaymentRepo
import com.eatthemoon.wallet.repository.PaymentRepoImpl
import kotlinx.coroutines.asExecutor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import kotlin.math.sin

val dbModule = module {
    single {
        val transactionQueryExecutor = get<AppCoroutineDispatchers>().io.asExecutor()

        Room
            .databaseBuilder(
                androidApplication(),
                WalletDB::class.java,
                WalletDB.DB_NAME
            )
            .setQueryExecutor(transactionQueryExecutor)
            .setTransactionExecutor(transactionQueryExecutor)
            .fallbackToDestructiveMigration()
            .build()
    }

    single<CurrentRepo> {
        CurrentRepoImpl(
            currentDao = get<WalletDB>().currentDao()
        )
    }

    single<PaymentRepo> {
        PaymentRepoImpl(
                paymentDao = get<WalletDB>().paymentDao()
        )
    }
}