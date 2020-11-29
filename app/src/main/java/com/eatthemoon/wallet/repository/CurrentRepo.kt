package com.eatthemoon.wallet.repository

import com.eatthemoon.wallet.db.entity.CurrentEntity
import kotlinx.coroutines.flow.Flow

interface CurrentRepo {
    suspend fun insert(currentEntity: CurrentEntity)
    fun observeCurrent() : Flow<CurrentEntity>
    fun observeIfCurrentInserted() : Flow<Boolean>
    suspend fun resetCurrent()
}