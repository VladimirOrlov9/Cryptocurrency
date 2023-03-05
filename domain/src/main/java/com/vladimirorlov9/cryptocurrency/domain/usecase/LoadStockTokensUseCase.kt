package com.vladimirorlov9.cryptocurrency.domain.usecase

import com.vladimirorlov9.cryptocurrency.domain.models.CoinInStock
import com.vladimirorlov9.cryptocurrency.domain.repository.CoinRepository
import com.vladimirorlov9.cryptocurrency.domain.repository.CurrenciesRepository
import kotlin.math.pow

class LoadStockTokensUseCase(
    private val coinRepository: CoinRepository,
    private val currenciesRepository: CurrenciesRepository
) {

    suspend fun execute(uid: Int): List<CoinInStock> {
        val localCoinsInfo = coinRepository.getStockList(uid = uid)
        return localCoinsInfo.map {  localCoin ->
            val coinCourse = currenciesRepository.getCoinCourse(localCoin.serverId)
            CoinInStock(
                id = localCoin.serverId,
                coinName = localCoin.name,
                coinShortName = localCoin.symbol,
                coinPrice = coinCourse.round(2),
                availableCoins = localCoin.amount,
                convertedAvailable = (coinCourse * localCoin.amount).round(2),
                logoUrl = localCoin.logo
            )
        }
    }
}

fun Double.round(symbols: Int): Double = (this * 10.0.pow(symbols)).toInt() / 10.0.pow(symbols)