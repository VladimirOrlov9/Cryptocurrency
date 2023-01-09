package com.vladimirorlov9.cryptocurrency.data.api.models

data class LatestCryptoStatus(
    val serverCode: Int,
    val cryptos: List<Crypto>?
)

data class Crypto(
    val name: String,
    val price: Double
)
