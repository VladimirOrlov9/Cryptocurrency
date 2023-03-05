package com.vladimirorlov9.cryptocurrency.domain.models

data class CoinScreenModel(
    val coinInfo: CoinInfoModel,
    val coinCourse: Double,
    val localAmount: Double
)
