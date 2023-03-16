package com.vladimirorlov9.cryptocurrency.domain.repository

import com.vladimirorlov9.cryptocurrency.domain.models.NewUser
import com.vladimirorlov9.cryptocurrency.domain.models.UserFullInfo
import com.vladimirorlov9.cryptocurrency.domain.models.UserOverviewModel

interface UserRepository {

    suspend fun signUp(user: NewUser): Long?

    suspend fun getBalance(userId: Long): Double

    suspend fun getUserOverviewData(userId: Int): UserOverviewModel

    suspend fun getFullUserInfo(uid: Int): UserFullInfo

    suspend fun updateUserPicture(uid: Int, fileName: String): UserOverviewModel
}