package com.vladimirorlov9.cryptocurrency.domain.usecase

import com.vladimirorlov9.cryptocurrency.domain.models.CoinScreenModel
import com.vladimirorlov9.cryptocurrency.domain.repository.CoinRepository
import com.vladimirorlov9.cryptocurrency.domain.repository.CurrenciesRepository

class LoadCoinInfoUseCase(
    private val currenciesRepository: CurrenciesRepository,
    private val coinRepository: CoinRepository
) {

    suspend fun execute(coinId: String, userId: Int): CoinScreenModel {
        return CoinScreenModel(
            coinInfo = currenciesRepository.getCoinInfo(coinId),
            coinCourse = currenciesRepository.getCoinCourse(coinId),
            localAmount = coinRepository.getStockCoinInfo(
                coinServerId = coinId,
                userId = userId
            )?.amount ?: 0.0
        )
    }
}