package com.vladimirorlov9.cryptocurrency.domain.usecase

import com.vladimirorlov9.cryptocurrency.domain.models.UserOverviewModel
import com.vladimirorlov9.cryptocurrency.domain.repository.UserRepository

class UpdateUserPictureUseCase(
    private val userRepository: UserRepository
) {
    suspend fun execute(uid: Int, fileName: String): UserOverviewModel {
        return userRepository.updateUserPicture(uid, fileName)
    }
}