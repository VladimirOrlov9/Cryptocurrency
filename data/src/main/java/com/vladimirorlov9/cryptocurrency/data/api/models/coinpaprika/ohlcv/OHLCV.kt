package com.vladimirorlov9.cryptocurrency.data.api.models.coinpaprika.ohlcv

import com.google.gson.annotations.SerializedName

data class OHLCV(
    val close: Double,
    val high: Double,
    val low: Double,
    @SerializedName("market_cap") val marketCap: Long,
    @SerializedName("open") val openValue: Double,
    @SerializedName("time_close") val timeClose: String,
    @SerializedName("time_open") val timeOpen: String,
    val volume: Long
)