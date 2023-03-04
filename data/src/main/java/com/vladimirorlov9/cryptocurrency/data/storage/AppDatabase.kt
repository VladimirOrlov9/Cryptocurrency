package com.vladimirorlov9.cryptocurrency.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vladimirorlov9.cryptocurrency.data.storage.models.CoinEntity
import com.vladimirorlov9.cryptocurrency.data.storage.models.NewUserEntity
import com.vladimirorlov9.cryptocurrency.data.storage.models.SpecificationEntity

@Database(
    entities = [SpecificationEntity::class, NewUserEntity::class, CoinEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun specificationsDao(): SpecificationsDao
    abstract fun usersDao(): UsersDao
    abstract fun coinsDao(): CoinsDao
}