package com.vladimirorlov9.cryptocurrency.data.api.models.coinmarketcap.lateststatus.response

data class Platform(
    val id: Int,
    val name: String,
    val slug: String,
    val symbol: String,
    val token_address: String
)