package com.vladimirorlov9.cryptocurrency.data.storage.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewUserEntity(
    @PrimaryKey(autoGenerate = true) var uid: Int = 0,
    @ColumnInfo val firstName: String,
    @ColumnInfo val lastName: String,
    @ColumnInfo val email: String,
    @ColumnInfo val phone: String,
    @ColumnInfo val password: String,
    @ColumnInfo val registrationDate: Long,
    @ColumnInfo(defaultValue = "1500.0") var balance: Double = 1500.0
)
