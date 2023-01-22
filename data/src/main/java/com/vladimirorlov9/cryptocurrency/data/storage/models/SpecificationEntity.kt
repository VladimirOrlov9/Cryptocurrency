package com.vladimirorlov9.cryptocurrency.data.storage.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "specifications")
data class SpecificationEntity(
    @PrimaryKey val specification: String,
    @ColumnInfo(name = "status") val status: Boolean = false
)