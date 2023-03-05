package com.vladimirorlov9.cryptocurrency.data.api

import com.vladimirorlov9.cryptocurrency.data.api.models.coinpaprika.coininfo.CoinInfo
import com.vladimirorlov9.cryptocurrency.data.api.models.coinpaprika.coins.CoinItem
import com.vladimirorlov9.cryptocurrency.data.api.models.coinpaprika.history.CoinHistoryPoint
import com.vladimirorlov9.cryptocurrency.data.api.models.coinpaprika.ohlcv.OHLCV

interface CurrenciesApiInterface {

    suspend fun getCoins(): List<CoinItem>
    suspend fun getCoinInfo(coinId: String): CoinInfo?
    suspend fun getCoinHistory(coinId: String, startDate: Long, interval: Int): List<CoinHistoryPoint>

    suspend fun getTodayOHLCV(coinId: String): OHLCV
}