package com.vladimirorlov9.cryptocurrency.utils.models

data class CoinInfoForBuy(
    var id: String? = null,
    var name: String? = null,
    var symbol: String? = null,
    var logo: String? = null,
    var price: Double? = null
) {
    fun isInitialized(): Boolean {
        return id != null && name != null && symbol != null &&
                logo != null && price != null
    }
}
