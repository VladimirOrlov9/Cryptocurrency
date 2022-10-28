package com.vladimirorlov9.cryptocurrency.domain.usecase

import com.vladimirorlov9.cryptocurrency.domain.models.UserName
import com.vladimirorlov9.cryptocurrency.domain.repository.UserRepository

class SaveUserNameUseCase(private val userRepository: UserRepository) {

    fun execute(userName: UserName): Boolean {
        if (userName.name.isBlank())
            return false

        userRepository.save(userName)
        return true
    }
}