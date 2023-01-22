package com.vladimirorlov9.cryptocurrency.domain.repository

import com.vladimirorlov9.cryptocurrency.domain.models.NewUser

interface UserRepository {

    suspend fun signUp(user: NewUser): Boolean
}