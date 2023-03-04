package com.vladimirorlov9.cryptocurrency.domain.repository

import com.vladimirorlov9.cryptocurrency.domain.models.BuyCoin

interface CoinRepository {

    suspend fun buyCoin(userId: Int, buyCoin: BuyCoin, price: Double): Double
    suspend fun getStockList(uid: Int): List<BuyCoin>
}