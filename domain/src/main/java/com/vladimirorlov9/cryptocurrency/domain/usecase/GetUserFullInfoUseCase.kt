package com.vladimirorlov9.cryptocurrency.domain.usecase

import com.vladimirorlov9.cryptocurrency.domain.models.NewUser
import com.vladimirorlov9.cryptocurrency.domain.repository.UserRepository

class GetUserFullInfoUseCase(
    private val userRepository: UserRepository
) {
    suspend fun execute(uid: Int): NewUser {
        return userRepository.getFullUserInfo(uid = uid)
    }
}