package com.vladimirorlov9.cryptocurrency.data.storage

import androidx.room.*
import com.vladimirorlov9.cryptocurrency.data.storage.models.CoinEntity
import com.vladimirorlov9.cryptocurrency.data.storage.models.NewUserEntity
import com.vladimirorlov9.cryptocurrency.domain.models.BuyCoin
import com.vladimirorlov9.cryptocurrency.domain.repository.CoinRepository

@Dao
interface CoinsDao : CoinRepository {

    override fun getStockCoinInfo(coinServerId: String, userId: Int): BuyCoin? {
        return getCoinByServerId(serverId = coinServerId, userId = userId)?.mapToBuyCoin()
    }

    override suspend fun getStockList(uid: Int): List<BuyCoin> {
        return getStockTokens(uid).map { coinEntity ->
            coinEntity.mapToBuyCoin()
        }
    }

    @Query("SELECT * FROM coins WHERE userId LIKE :uid")
    fun getStockTokens(uid: Int): List<CoinEntity>

    override suspend fun buyCoin(userId: Int, buyCoin: BuyCoin, price: Double): Double {
        val existingCoin = getCoinByServerId(buyCoin.serverId, userId)
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
            price = price,
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

    @Query("SELECT * FROM coins WHERE serverId LIKE :serverId AND userId LIKE :userId LIMIT 1")
    fun getCoinByServerId(serverId: String, userId: Int): CoinEntity?

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

    private fun CoinEntity.mapToBuyCoin(): BuyCoin {
        return BuyCoin(
            serverId = this.serverId,
            logo = this.logo,
            name = this.name,
            symbol = this.symbol,
            amount = this.amount
        )
    }
}