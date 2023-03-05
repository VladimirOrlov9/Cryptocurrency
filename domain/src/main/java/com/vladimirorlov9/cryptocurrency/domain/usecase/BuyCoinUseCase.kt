package com.vladimirorlov9.cryptocurrency.domain.usecase

import com.vladimirorlov9.cryptocurrency.domain.constants.baseBalance
import com.vladimirorlov9.cryptocurrency.domain.models.BalanceInfo
import com.vladimirorlov9.cryptocurrency.domain.models.BuyCoin
import com.vladimirorlov9.cryptocurrency.domain.repository.CoinRepository
import kotlin.math.roundToInt

class BuyCoinUseCase(private val coinRepository: CoinRepository) {

    suspend fun execute(userId: Int, buyCoin: BuyCoin, price: Double): BalanceInfo {
        val balance = coinRepository.buyCoin(
            userId = userId,
            buyCoin = buyCoin,
            price = price
        )
        val balanceRounded = (balance * 100).toInt() / 100.0
        val percentDifference = ((baseBalance - balanceRounded) / baseBalance).roundToInt()
        return BalanceInfo(
            balance = balanceRounded,
            percentIncrease = percentDifference
        )
    }
}