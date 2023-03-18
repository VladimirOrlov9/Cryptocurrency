package com.vladimirorlov9.cryptocurrency.domain.usecase

import com.vladimirorlov9.cryptocurrency.domain.models.UserOverviewModel
import com.vladimirorlov9.cryptocurrency.domain.repository.UserRepository

class DeleteProfilePictureUseCase(
    private val userRepository: UserRepository
) {

    suspend fun execute(uid: Int):UserOverviewModel {
        return userRepository.deleteProfilePicture(uid)
    }
}