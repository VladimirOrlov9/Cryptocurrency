package com.vladimirorlov9.cryptocurrency.domain.repository

import com.vladimirorlov9.cryptocurrency.domain.models.CoinHistoryModel
import com.vladimirorlov9.cryptocurrency.domain.models.CoinInfoModel
import com.vladimirorlov9.cryptocurrency.domain.models.SearchCoin

interface CurrenciesRepository {

    suspend fun getCoins(): List<SearchCoin>
    suspend fun getCoinInfo(coinId: String): CoinInfoModel
    suspend fun getCoinHistory(coinId: String, start: Long, interval: Int): List<CoinHistoryModel>
}