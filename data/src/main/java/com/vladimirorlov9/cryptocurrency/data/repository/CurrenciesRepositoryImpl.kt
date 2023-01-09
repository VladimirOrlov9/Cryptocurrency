package com.vladimirorlov9.cryptocurrency.data.repository

import com.vladimirorlov9.cryptocurrency.data.api.CurrenciesApiInterface
import com.vladimirorlov9.cryptocurrency.data.api.models.LatestCryptoStatus
import com.vladimirorlov9.cryptocurrency.domain.models.CryptoPrice
import com.vladimirorlov9.cryptocurrency.domain.models.CurrenciesStatus
import com.vladimirorlov9.cryptocurrency.domain.repository.CurrenciesRepository
import kotlinx.coroutines.*

class CurrenciesRepositoryImpl(private val currenciesApiInterface: CurrenciesApiInterface): CurrenciesRepository {

    override suspend fun getStatus(): CurrenciesStatus {
        return currenciesApiInterface.getLatest().mapToCurrenciesStatus()
    }

    private fun LatestCryptoStatus.mapToCurrenciesStatus(): CurrenciesStatus {
        return CurrenciesStatus(
            this.serverCode,
            this.cryptos?.map {
            CryptoPrice(
                name = it.name,
                price = it.price
            )
        })
    }
}