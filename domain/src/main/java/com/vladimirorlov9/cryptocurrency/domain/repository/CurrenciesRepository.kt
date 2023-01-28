package com.vladimirorlov9.cryptocurrency.domain.repository

import com.vladimirorlov9.cryptocurrency.domain.models.SearchCoin

interface CurrenciesRepository {

    suspend fun getCoins(): List<SearchCoin>
}