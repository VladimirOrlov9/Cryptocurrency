package com.vladimirorlov9.cryptocurrency.data.repository

import com.vladimirorlov9.cryptocurrency.data.storage.UserStorageInterface
import com.vladimirorlov9.cryptocurrency.data.storage.models.User
import com.vladimirorlov9.cryptocurrency.domain.models.UserName
import com.vladimirorlov9.cryptocurrency.domain.repository.UserRepository

class UserRepositoryImpl(private val userStorageInterface: UserStorageInterface): UserRepository {

    override fun save(userName: UserName) {
        userStorageInterface.save(mapToUser(userName))
    }

    override fun get(): UserName {
        val user = userStorageInterface.get()
        return mapToUserName(user)
    }

    private fun mapToUser(userName: UserName): User {
        return User(
            name = userName.name
        )
    }

    private fun mapToUserName(user: User): UserName {
        return UserName(
            name = user.name
        )
    }
}