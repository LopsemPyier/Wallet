package com.eatthemoon.wallet.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_ID = 0
const val CURRENT_TABLE_NAME = "current_table"

@Entity(tableName = CURRENT_TABLE_NAME)
data class CurrentEntity(
    var amount: Double,
    @PrimaryKey(autoGenerate = false)
    var ID: Int = CURRENT_ID
)