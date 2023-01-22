package com.vladimirorlov9.cryptocurrency.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vladimirorlov9.cryptocurrency.data.storage.models.SpecificationEntity

@Database(entities = [SpecificationEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun specificationsDao(): SpecificationsDao
}