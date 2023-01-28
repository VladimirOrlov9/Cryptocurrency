package com.vladimirorlov9.cryptocurrency.data.api.retrofit2

import com.vladimirorlov9.cryptocurrency.data.api.models.coinpaprika.coins.CoinItem
import retrofit2.Call
import retrofit2.http.GET

interface CoinPaprikaApiService {

    @GET("coins")
    fun getCoins(): Call<List<CoinItem>>

    companion object {
        const val BASE_URL = "https://api.coinpaprika.com/v1/"
    }
}