package com.vladimirorlov9.cryptocurrency.data.storage.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins")
data class CoinEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo val userId: Int,
    @ColumnInfo val serverId: String,
    @ColumnInfo val logo: String,
    @ColumnInfo val name: String,
    @ColumnInfo val symbol: String,
    @ColumnInfo var amount: Double
)
