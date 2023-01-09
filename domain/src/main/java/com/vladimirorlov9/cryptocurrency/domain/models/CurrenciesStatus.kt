package com.vladimirorlov9.cryptocurrency.domain.models

data class CurrenciesStatus(
    val serverCode: Int,
    val prices: List<CryptoPrice>?
)
