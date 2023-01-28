package com.vladimirorlov9.cryptocurrency.data.api.models.coinpaprika.coins

data class CoinItem(
    val id: String,
    // TODO check active
    val is_active: Boolean,
    val is_new: Boolean,
    val name: String,
    val rank: Int,
    val symbol: String,
    val type: String
)