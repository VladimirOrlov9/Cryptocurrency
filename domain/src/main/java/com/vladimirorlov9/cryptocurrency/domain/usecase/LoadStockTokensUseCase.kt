package com.vladimirorlov9.cryptocurrency.domain.usecase

import com.vladimirorlov9.cryptocurrency.domain.models.CoinInStock
import com.vladimirorlov9.cryptocurrency.domain.repository.CoinRepository

class LoadStockTokensUseCase(private val coinRepository: CoinRepository) {

    suspend fun execute(uid: Int): List<CoinInStock> {
        val localCoinsInfo = coinRepository.getStockList(uid = uid)
        return localCoinsInfo.map {  localCoin ->
            CoinInStock(
                id = localCoin.serverId,
                coinName = localCoin.name,
                coinShortName = localCoin.symbol,
                coinPrice = 0.0,
                availableCoins = localCoin.amount,
                convertedAvailable = 0.0,
                logoUrl = localCoin.logo
            )
        }
    }
}