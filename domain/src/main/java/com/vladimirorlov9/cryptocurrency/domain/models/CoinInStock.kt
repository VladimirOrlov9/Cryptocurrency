package com.vladimirorlov9.cryptocurrency.domain.models

data class CoinInStock(
    val id: String,
    val coinName: String,
    val coinShortName: String,
    val coinPrice: Double,
    val availableCoins: Double,
    val convertedAvailable: Double,
    val logoUrl: String
)
