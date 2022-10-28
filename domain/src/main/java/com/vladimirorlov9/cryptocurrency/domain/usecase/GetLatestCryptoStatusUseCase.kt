package com.vladimirorlov9.cryptocurrency.domain.usecase

import com.vladimirorlov9.cryptocurrency.domain.models.CurrenciesStatus
import com.vladimirorlov9.cryptocurrency.domain.repository.CurrenciesRepository

class GetLatestCryptoStatusUseCase(private val currenciesRepository: CurrenciesRepository) {

    suspend fun execute(): CurrenciesStatus {
        return currenciesRepository.getStatus()
    }
}