package com.vladimirorlov9.cryptocurrency.data.repository

import com.vladimirorlov9.cryptocurrency.data.api.CurrenciesApiInterface
import com.vladimirorlov9.cryptocurrency.data.api.models.coinpaprika.coins.CoinItem
import com.vladimirorlov9.cryptocurrency.domain.models.SearchCoin
import com.vladimirorlov9.cryptocurrency.domain.repository.CurrenciesRepository

class CurrenciesRepositoryImpl(
    private val currenciesApiInterface: CurrenciesApiInterface
) : CurrenciesRepository {

    override suspend fun getCoins(): List<SearchCoin> {
        return currenciesApiInterface.getCoins().mapToSearchCoinList()
    }

    private fun List<CoinItem>.mapToSearchCoinList(): List<SearchCoin> {
        return this.map {
            SearchCoin(
                id = it.id,
                name = it.name,
                nameShort = it.symbol,
                price = 0.0
            )
        }
    }
}