package com.vladimirorlov9.cryptocurrency.domain.models

data class BuyCoin(
    val serverId: String,
    val logo: String,
    val name: String,
    val symbol: String,
    val amount: Double
)
