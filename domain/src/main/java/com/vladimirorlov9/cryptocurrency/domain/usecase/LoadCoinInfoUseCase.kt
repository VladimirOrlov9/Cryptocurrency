package com.vladimirorlov9.cryptocurrency.domain.usecase

import com.vladimirorlov9.cryptocurrency.domain.models.CoinInfoModel
import com.vladimirorlov9.cryptocurrency.domain.repository.CurrenciesRepository

class LoadCoinInfoUseCase(private val currenciesRepository: CurrenciesRepository) {

    suspend fun execute(coinId: String): CoinInfoModel {
        return currenciesRepository.getCoinInfo(coinId)
    }
}