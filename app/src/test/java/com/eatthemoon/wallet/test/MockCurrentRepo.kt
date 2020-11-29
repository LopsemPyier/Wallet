package com.eatthemoon.wallet.test

import com.eatthemoon.wallet.db.entity.CurrentEntity
import com.eatthemoon.wallet.repository.CurrentRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MockCurrentRepo(
    private val observedCurrent: CurrentEntity
): CurrentRepo {

    val addCurrentRender = RenderRecorder<CurrentEntity>()

    override suspend fun insert(currentEntity: CurrentEntity) =
        addCurrentRender.add(currentEntity)

    override fun observeCurrent(): Flow<CurrentEntity> = flowOf(observedCurrent)
    override fun observeIfCurrentInserted(): Flow<Boolean> = flowOf(true)

    override suspend fun resetCurrent() {
        addCurrentRender.reset()
    }

    fun reset() {
        addCurrentRender.reset()
    }
}