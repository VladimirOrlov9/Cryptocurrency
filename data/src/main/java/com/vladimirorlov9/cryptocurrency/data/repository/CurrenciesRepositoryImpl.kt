package com.vladimirorlov9.cryptocurrency.data.repository

import com.vladimirorlov9.cryptocurrency.data.api.CurrenciesApiInterface
import com.vladimirorlov9.cryptocurrency.data.api.models.coinpaprika.coininfo.CoinInfo
import com.vladimirorlov9.cryptocurrency.data.api.models.coinpaprika.coins.CoinItem
import com.vladimirorlov9.cryptocurrency.data.api.models.coinpaprika.history.CoinHistoryPoint
import com.vladimirorlov9.cryptocurrency.domain.models.CoinHistoryModel
import com.vladimirorlov9.cryptocurrency.domain.models.CoinInfoModel
import com.vladimirorlov9.cryptocurrency.domain.models.SearchCoin
import com.vladimirorlov9.cryptocurrency.domain.repository.CurrenciesRepository
import kotlin.math.roundToInt

class CurrenciesRepositoryImpl(
    private val currenciesApiInterface: CurrenciesApiInterface
) : CurrenciesRepository {

    override suspend fun getCoins(): List<SearchCoin> {
        return currenciesApiInterface.getCoins().mapToSearchCoinList()
    }

    override suspend fun getCoinInfo(coinId: String): CoinInfoModel {
        return currenciesApiInterface.getCoinInfo(coinId).mapCoinInfoToCoinInfoModel()
    }

    override suspend fun getCoinHistory(
        coinId: String,
        start: Long,
        interval: Int
    ): List<CoinHistoryModel> {
        return currenciesApiInterface.getCoinHistory(
            coinId = coinId,
            startDate = start,
            interval = interval
        ).mapCoinHistoryPointToCoinHistoryModel()
    }

    private fun List<CoinHistoryPoint>.mapCoinHistoryPointToCoinHistoryModel(): List<CoinHistoryModel> {
        return this.map {
            CoinHistoryModel(
                timestamp = it.timestamp,
                price = it.price
            )
        }
    }

    private fun CoinInfo?.mapCoinInfoToCoinInfoModel(): CoinInfoModel {
        return this?.let {
            CoinInfoModel(
                name = it.name,
                symbol = it.symbol,
                logo = it.logo
            )
        } ?: CoinInfoModel("","","")
    }

    private fun List<CoinItem>.mapToSearchCoinList(): List<SearchCoin> {
        val filteredList = this.filter { it.is_active }
        return filteredList.map {
            SearchCoin(
                id = it.id,
                name = it.name,
                nameShort = it.symbol,
                price = 0.0
            )
        }
    }
}