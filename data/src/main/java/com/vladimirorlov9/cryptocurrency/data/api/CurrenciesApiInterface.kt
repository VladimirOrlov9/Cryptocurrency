package com.vladimirorlov9.cryptocurrency.data.api

import com.vladimirorlov9.cryptocurrency.data.api.models.coinpaprika.coins.CoinItem

interface CurrenciesApiInterface {

    suspend fun getCoins(): List<CoinItem>
}