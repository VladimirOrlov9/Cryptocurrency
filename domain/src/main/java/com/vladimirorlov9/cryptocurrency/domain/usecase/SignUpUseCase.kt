package com.vladimirorlov9.cryptocurrency.domain.usecase

import com.vladimirorlov9.cryptocurrency.domain.models.NewUser
import com.vladimirorlov9.cryptocurrency.domain.repository.UserRepository

class SignUpUseCase(private val userRepository: UserRepository) {

    suspend fun execute(user: NewUser): Long? {
        return userRepository.signUp(user)
    }
}