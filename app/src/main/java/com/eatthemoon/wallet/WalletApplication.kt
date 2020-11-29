package com.eatthemoon.wallet

import android.app.Application
import com.eatthemoon.wallet.di.*
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WalletApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@WalletApplication)

            modules(
                dbModule,
                useCaseModule,
                dispatchersModule,
                homeVMModule,
                currentVMModule,
                newVMModule,
                listVMModule,
                editVMModule,
                detailVMModule
            )
        }

        AndroidThreeTen.init(this)
    }
}