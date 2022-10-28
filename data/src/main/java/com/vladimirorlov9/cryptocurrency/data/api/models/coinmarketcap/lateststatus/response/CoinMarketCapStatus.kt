package com.vladimirorlov9.cryptocurrency.data.api.models.coinmarketcap.lateststatus.response

data class CoinMarketCapStatus(
    val `data`: List<Data>,
    val status: Status
)