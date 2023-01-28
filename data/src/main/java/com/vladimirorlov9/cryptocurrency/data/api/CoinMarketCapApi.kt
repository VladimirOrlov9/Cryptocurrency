package com.vladimirorlov9.cryptocurrency.data.api

import com.vladimirorlov9.cryptocurrency.data.api.models.Crypto
import com.vladimirorlov9.cryptocurrency.data.api.models.LatestCryptoStatus
import com.vladimirorlov9.cryptocurrency.data.api.models.coinmarketcap.lateststatus.response.CoinMarketCapStatus
import com.vladimirorlov9.cryptocurrency.data.api.retrofit2.CoinMarketCapService
import com.vladimirorlov9.data.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CoinMarketCapApi {

    private val retrofit = Retrofit.Builder()
        .baseUrl(CoinMarketCapService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: CoinMarketCapService =
        retrofit.create(CoinMarketCapService::class.java)

    suspend fun getLatest(): LatestCryptoStatus {
        // TODO move {api_key} from @Query to @Header
        val call = service.getCurrenciesStatus(
            apiKey = BuildConfig.COIN_MARKET_CAP_API_KEY
        )
        val response = call.execute()
        return mapToLatestCryptoStatus(
            response.code(),
            response.body()
        )
    }

    private fun mapToLatestCryptoStatus(
        serverCode: Int,
        coinMarketCapStatus: CoinMarketCapStatus?
    ): LatestCryptoStatus {
        return LatestCryptoStatus(
            serverCode = serverCode,
            coinMarketCapStatus?.let {
                coinMarketCapStatus.data.map {
                    Crypto(
                        name = it.name,
                        price = it.quote.USD.price
                    )
                }
            }
        )
    }
}