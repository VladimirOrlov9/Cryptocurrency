package com.vladimirorlov9.cryptocurrency.domain.usecase

import com.vladimirorlov9.cryptocurrency.domain.repository.UserRepository

class UpdateUserPictureUseCase(
    private val userRepository: UserRepository
) {
    suspend fun execute(uid: Int, fileName: String) {
        userRepository.updateUserPicture(uid, fileName)
    }
}