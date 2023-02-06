package com.vladimirorlov9.cryptocurrency.data.api

import com.vladimirorlov9.cryptocurrency.data.api.models.coinpaprika.coininfo.CoinInfo
import com.vladimirorlov9.cryptocurrency.data.api.models.coinpaprika.coins.CoinItem
import com.vladimirorlov9.cryptocurrency.data.api.models.coinpaprika.history.CoinHistoryPoint

interface CurrenciesApiInterface {

    suspend fun getCoins(): List<CoinItem>
    suspend fun getCoinInfo(coinId: String): CoinInfo?
    suspend fun getCoinHistory(coinId: String, startDate: Long, interval: Int): List<CoinHistoryPoint>
}