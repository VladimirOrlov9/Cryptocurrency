package com.vladimirorlov9.cryptocurrency.domain.usecase

import com.vladimirorlov9.cryptocurrency.domain.models.UserOverviewModel
import com.vladimirorlov9.cryptocurrency.domain.repository.UserRepository

class UpdateUserInfoPictureUseCase(
    private val userRepository: UserRepository
) {

    suspend fun execute(
        uid: Int,
        firstName: String,
        lastName: String,
        email: String,
        tradeName: String,
        phoneNumber: String,
        birthday: Long
    ): UserOverviewModel {
        return userRepository.updateUserInfo(
            uid = uid,
            firstName = firstName,
            lastName = lastName,
            email = email,
            tradeName = tradeName,
            phoneNumber = phoneNumber,
            birthday = birthday
        )
    }
}