package com.vladimirorlov9.cryptocurrency.domain.usecase

import com.vladimirorlov9.cryptocurrency.domain.models.CoinHistoryModel
import com.vladimirorlov9.cryptocurrency.domain.repository.CurrenciesRepository

class LoadHistoricalCoinDataUseCase(private val currenciesRepository: CurrenciesRepository) {

    suspend fun execute(coinId: String, days: Int): List<CoinHistoryModel> {
        val daysInSeconds = days * 24 * 60 * 60
        val currentTimeInSeconds = System.currentTimeMillis() / 1000
        val startDateInSeconds = currentTimeInSeconds - daysInSeconds
        val interval = when {
            days >= 300 -> 30
            days >= 140 -> 14
            days >= 70 -> 7
            else -> 1
        }
        return currenciesRepository.getCoinHistory(coinId, startDateInSeconds, interval)
    }
}