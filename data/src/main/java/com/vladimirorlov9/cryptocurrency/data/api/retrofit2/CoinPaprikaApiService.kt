package com.vladimirorlov9.cryptocurrency.data.api.retrofit2

import com.vladimirorlov9.cryptocurrency.data.api.models.coinpaprika.coininfo.CoinInfo
import com.vladimirorlov9.cryptocurrency.data.api.models.coinpaprika.coins.CoinItem
import com.vladimirorlov9.cryptocurrency.data.api.models.coinpaprika.history.CoinHistoryPoint
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinPaprikaApiService {

    @GET("coins")
    fun getCoins(): Call<List<CoinItem>>

    @GET("coins/{id}")
    fun getCoinInfo(@Path("id") coinId: String): Call<CoinInfo>

    @GET("tickers/{id}/historical")
    fun getCoinHistory(
        @Path("id") coinId: String,
        @Query("start") startDate: Long,
        @Query("interval") interval: String
    ): Call<List<CoinHistoryPoint>>

    companion object {
        const val BASE_URL = "https://api.coinpaprika.com/v1/"
    }
}