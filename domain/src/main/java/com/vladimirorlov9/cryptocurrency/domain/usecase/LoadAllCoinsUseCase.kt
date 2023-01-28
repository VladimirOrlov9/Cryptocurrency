package com.vladimirorlov9.cryptocurrency.domain.usecase

import com.vladimirorlov9.cryptocurrency.domain.models.SearchCoin
import com.vladimirorlov9.cryptocurrency.domain.repository.CurrenciesRepository

class LoadAllCoinsUseCase(private val currenciesRepository: CurrenciesRepository) {

    suspend fun execute(): List<SearchCoin> {
        return currenciesRepository.getCoins()
    }
}