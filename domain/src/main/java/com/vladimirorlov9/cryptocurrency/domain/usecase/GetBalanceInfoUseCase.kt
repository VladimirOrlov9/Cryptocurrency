package com.vladimirorlov9.cryptocurrency.domain.usecase

import com.vladimirorlov9.cryptocurrency.domain.constants.baseBalance
import com.vladimirorlov9.cryptocurrency.domain.models.BalanceInfo
import com.vladimirorlov9.cryptocurrency.domain.repository.UserRepository
import kotlin.math.roundToInt

class GetBalanceInfoUseCase(private val userRepository: UserRepository) {

    suspend fun execute(userId: Long): BalanceInfo {
        val balance = userRepository.getBalance(userId)
        val percentDifference = ((baseBalance - balance) / baseBalance).roundToInt()
        return BalanceInfo(
            balance = balance,
            percentIncrease = percentDifference
        )
    }
}