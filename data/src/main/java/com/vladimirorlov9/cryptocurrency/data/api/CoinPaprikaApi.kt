package com.vladimirorlov9.cryptocurrency.data.api

import com.vladimirorlov9.cryptocurrency.data.api.models.coinpaprika.coins.CoinItem
import com.vladimirorlov9.cryptocurrency.data.api.retrofit2.CoinPaprikaApiService
import com.vladimirorlov9.cryptocurrency.domain.models.SearchCoin

class CoinPaprikaApi(private val apiService: CoinPaprikaApiService) : CurrenciesApiInterface {
    override suspend fun getCoins(): List<CoinItem> {
        val call = apiService.getCoins()
        val response = call.execute()
        return response.body() ?: listOf()
    }
}