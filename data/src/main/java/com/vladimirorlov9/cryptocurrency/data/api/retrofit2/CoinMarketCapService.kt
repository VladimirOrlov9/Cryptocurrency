package com.vladimirorlov9.cryptocurrency.data.api.retrofit2

import com.vladimirorlov9.cryptocurrency.data.api.models.coinmarketcap.lateststatus.response.CoinMarketCapStatus
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinMarketCapService {

    @GET("v1/cryptocurrency/listings/latest")
    fun getCurrenciesStatus(@Query("CMC_PRO_API_KEY") apiKey: String): Call<CoinMarketCapStatus>

    companion object {
        const val BASE_URL = "https://pro-api.coinmarketcap.com/"
    }
}