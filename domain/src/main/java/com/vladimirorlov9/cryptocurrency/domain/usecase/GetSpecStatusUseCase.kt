package com.vladimirorlov9.cryptocurrency.domain.usecase

import com.vladimirorlov9.cryptocurrency.domain.repository.SpecificationsRepository

class GetSpecStatusUseCase(private val specDao: SpecificationsRepository) {

    suspend fun execute(specName: String): Boolean {
        return specDao.getSpecStatus(specName)
    }
}