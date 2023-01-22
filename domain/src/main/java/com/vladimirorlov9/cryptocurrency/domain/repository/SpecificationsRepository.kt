package com.vladimirorlov9.cryptocurrency.domain.repository

interface SpecificationsRepository {
    
    suspend fun getSpecStatus(specification: String): Boolean
    suspend fun finishSpec(specName: String)
}