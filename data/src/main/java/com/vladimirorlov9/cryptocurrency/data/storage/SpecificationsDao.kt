package com.vladimirorlov9.cryptocurrency.data.storage

import androidx.room.*
import com.vladimirorlov9.cryptocurrency.data.storage.models.SpecificationEntity
import com.vladimirorlov9.cryptocurrency.domain.repository.SpecificationsRepository

@Dao
interface SpecificationsDao: SpecificationsRepository {

    @Query("SELECT status FROM specifications WHERE :specification LIKE specification")
    fun getSpecStatusLocal(specification: String): Boolean?

    @Insert
    fun insertSpecification(specification: SpecificationEntity)

    @Transaction
    override suspend fun getSpecStatus(specification: String): Boolean {
        val specStatus = getSpecStatusLocal(specification)
        if (specStatus == null) {
            insertSpecification(
                SpecificationEntity(
                    specification = specification
                )
            )
            return false
        }
        return specStatus
    }

    @Update
    fun updateSpec(spec: SpecificationEntity)

    @Transaction
    override suspend fun finishSpec(specName: String) {
        val spec = SpecificationEntity(
            specification = specName,
            status = true
        )
        updateSpec(spec)
    }

}