package com.vladimirorlov9.cryptocurrency.domain.models

data class SearchCoin(
    val id: String,
    val name: String,
    val nameShort: String,
    val price: Double
)
