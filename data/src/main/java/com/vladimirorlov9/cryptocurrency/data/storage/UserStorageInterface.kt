package com.vladimirorlov9.cryptocurrency.data.storage

import com.vladimirorlov9.cryptocurrency.data.storage.models.User

interface UserStorageInterface {

    fun save(user: User)
    fun get(): User
}