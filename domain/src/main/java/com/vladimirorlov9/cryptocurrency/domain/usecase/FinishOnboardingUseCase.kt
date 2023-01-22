package com.vladimirorlov9.cryptocurrency.domain.usecase

import com.vladimirorlov9.cryptocurrency.domain.repository.SpecificationsRepository

class FinishOnboardingUseCase(private val specDao: SpecificationsRepository) {

    suspend fun execute(specName: String) {
        specDao.finishSpec(specName)
    }
}