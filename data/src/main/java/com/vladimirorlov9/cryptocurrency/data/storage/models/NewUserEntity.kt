package com.vladimirorlov9.cryptocurrency.data.storage.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vladimirorlov9.cryptocurrency.domain.constants.baseBalance

@Entity(tableName = "users")
data class NewUserEntity (
    @PrimaryKey(autoGenerate = true) var uid: Int = 0,
    @ColumnInfo var firstName: String,
    @ColumnInfo var lastName: String,
    @ColumnInfo var email: String,
    @ColumnInfo var phone: String,
    @ColumnInfo var password: String,
    @ColumnInfo val registrationDate: Long,
    @ColumnInfo(defaultValue = "1500.0") var balance: Double = baseBalance,
    @ColumnInfo(defaultValue = "null") var image: String? = null,
    @ColumnInfo var tradeName: String,
    @ColumnInfo var birthday: Long
)
