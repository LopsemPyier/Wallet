package com.eatthemoon.wallet.repository

import com.eatthemoon.wallet.db.dao.CurrentDao
import com.eatthemoon.wallet.db.entity.CurrentEntity
import kotlinx.coroutines.flow.Flow

class CurrentRepoImpl(
    private val currentDao: CurrentDao
) : CurrentRepo {
    override suspend fun insert(currentEntity: CurrentEntity) =
        currentDao.insert(currentEntity)

    override fun observeCurrent(): Flow<CurrentEntity> =
        currentDao.getCurrent()

    override fun observeIfCurrentInserted(): Flow<Boolean> =
        currentDao.isCurrentInserted()

    override suspend fun resetCurrent() =
        currentDao.resetCurrent()
}