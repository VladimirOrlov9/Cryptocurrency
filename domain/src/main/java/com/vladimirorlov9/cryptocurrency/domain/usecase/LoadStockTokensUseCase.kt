package com.vladimirorlov9.cryptocurrency.domain.usecase

import com.vladimirorlov9.cryptocurrency.domain.models.CoinInStock
import com.vladimirorlov9.cryptocurrency.domain.repository.CoinRepository
import kotlin.math.pow

class LoadStockTokensUseCase(
    private val coinRepository: CoinRepository
) {

    suspend fun execute(uid: Int): List<CoinInStock> {
        val localCoinsInfo = coinRepository.getStockList(uid = uid)
        return localCoinsInfo.map {  localCoin ->
            CoinInStock(
                id = localCoin.serverId,
                coinName = localCoin.name,
                coinShortName = localCoin.symbol,
                availableCoins = localCoin.amount,
                logoUrl = localCoin.logo
            )
        }
    }
}

fun Double.round(symbols: Int): Double = (this * 10.0.pow(symbols)).toInt() / 10.0.pow(symbols)