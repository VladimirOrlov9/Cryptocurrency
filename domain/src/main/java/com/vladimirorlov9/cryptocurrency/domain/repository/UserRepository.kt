package com.vladimirorlov9.cryptocurrency.domain.repository

import com.vladimirorlov9.cryptocurrency.domain.models.UserName

interface UserRepository {

    fun save(userName: UserName)
    fun get(): UserName
}