package com.vladimirorlov9.cryptocurrency.data.api

import android.util.Log
import com.vladimirorlov9.cryptocurrency.data.api.models.coinpaprika.coininfo.CoinInfo
import com.vladimirorlov9.cryptocurrency.data.api.models.coinpaprika.coins.CoinItem
import com.vladimirorlov9.cryptocurrency.data.api.models.coinpaprika.history.CoinHistoryPoint
import com.vladimirorlov9.cryptocurrency.data.api.retrofit2.CoinPaprikaApiService
import com.vladimirorlov9.cryptocurrency.domain.models.SearchCoin

const val TAG = "CoinPaprikaApi"

class CoinPaprikaApi(private val apiService: CoinPaprikaApiService) : CurrenciesApiInterface {
    override suspend fun getCoins(): List<CoinItem> {
        val call = apiService.getCoins()
        val response = call.execute()
        return response.body() ?: listOf()
    }

    override suspend fun getCoinInfo(coinId: String): CoinInfo? {
        val call = apiService.getCoinInfo(coinId)
        val response = call.execute()
        return response.body()
    }

    override suspend fun getCoinHistory(
        coinId: String,
        startDate: Long,
        interval: Int
    ): List<CoinHistoryPoint> {
        val call = apiService.getCoinHistory(
            coinId = coinId,
            startDate = startDate,
            interval = "${interval}d"
        )
        Log.i(TAG, call.request().url().toString())
        val response = call.execute()
        return response.body() ?: listOf()
    }
}