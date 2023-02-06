package com.vladimirorlov9.cryptocurrency.data.api.models.coinpaprika.history

data class CoinHistoryPoint(
    val market_cap: Long,
    val price: Double,
    val timestamp: String,
    val volume_24h: Long
)