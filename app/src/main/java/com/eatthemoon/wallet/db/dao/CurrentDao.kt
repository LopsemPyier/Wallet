package com.eatthemoon.wallet.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eatthemoon.wallet.db.entity.CURRENT_ID
import com.eatthemoon.wallet.db.entity.CURRENT_TABLE_NAME
import com.eatthemoon.wallet.db.entity.CurrentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currentEntity: CurrentEntity)

    @Query("SELECT * FROM $CURRENT_TABLE_NAME WHERE ID = $CURRENT_ID")
    fun getCurrent(): Flow<CurrentEntity>

    @Query("SELECT EXISTS(SELECT * FROM $CURRENT_TABLE_NAME)") // TODO : Be sure it's needed and I can't get it with getCurrent only
    fun isCurrentInserted(): Flow<Boolean>

    @Query("DELETE FROM $CURRENT_TABLE_NAME")
    suspend fun resetCurrent()
}