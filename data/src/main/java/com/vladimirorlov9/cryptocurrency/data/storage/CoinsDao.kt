package com.vladimirorlov9.cryptocurrency.data.storage

import androidx.room.*
import com.vladimirorlov9.cryptocurrency.data.storage.models.CoinEntity
import com.vladimirorlov9.cryptocurrency.data.storage.models.NewUserEntity
import com.vladimirorlov9.cryptocurrency.domain.models.BuyCoin
import com.vladimirorlov9.cryptocurrency.domain.models.CryptoPrice
import com.vladimirorlov9.cryptocurrency.domain.repository.CoinRepository

@Dao
interface CoinsDao : CoinRepository {

    override suspend fun buyCoin(userId: Int, buyCoin: BuyCoin): Double {
        val existingCoin = getCoinByServerId(buyCoin.serverId)
        if (existingCoin == null) {
            insertCoin(
                coin = buyCoin.mapToCoinEntity(
                    userId = userId
                )
            )
        } else {
            existingCoin.amount += buyCoin.amount
            updateCoin(
                coin = existingCoin
            )
        }
        return updateUserBalance(
            incOrDec = false,
            price = buyCoin.price,
            uid = userId
        )
    }

    @Update(entity = CoinEntity::class)
    fun updateCoin(coin: CoinEntity)

    @Transaction
    fun updateUserBalance(incOrDec: Boolean, price: Double, uid: Int): Double {
        val user = getUserInfo(uid = uid)
        if (incOrDec) {
            user.balance += price
        } else {
            user.balance -= price
        }
        updateUserInfo(user)

        return user.balance
    }

    @Update(entity = NewUserEntity::class)
    fun updateUserInfo(user: NewUserEntity)

    @Query("SELECT * FROM users WHERE uid LIKE :uid LIMIT 1")
    fun getUserInfo(uid: Int): NewUserEntity

    @Query("SELECT * FROM coins WHERE serverId LIKE :serverId LIMIT 1")
    fun getCoinByServerId(serverId: String): CoinEntity?

    @Insert
    fun insertCoin(coin: CoinEntity)

    fun BuyCoin.mapToCoinEntity(userId: Int): CoinEntity {
        return CoinEntity(
            serverId = this.serverId,
            logo = this.logo,
            name = this.name,
            symbol = this.symbol,
            amount = this.amount,
            userId = userId
        )
    }
}