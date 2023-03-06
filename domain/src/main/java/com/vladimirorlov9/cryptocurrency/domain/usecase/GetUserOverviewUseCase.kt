package com.vladimirorlov9.cryptocurrency.domain.usecase

import com.vladimirorlov9.cryptocurrency.domain.models.UserOverviewModel
import com.vladimirorlov9.cryptocurrency.domain.repository.UserRepository

class GetUserOverviewUseCase(
    private val userRepository: UserRepository
) {

    suspend fun execute(uid: Int): UserOverviewModel {
        return userRepository.getUserOverviewData(uid)
    }
}