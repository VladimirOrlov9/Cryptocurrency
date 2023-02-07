package com.vladimirorlov9.cryptocurrency.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.vladimirorlov9.cryptocurrency.data.storage.models.NewUserEntity
import com.vladimirorlov9.cryptocurrency.domain.models.NewUser
import com.vladimirorlov9.cryptocurrency.domain.repository.UserRepository

@Dao
interface UsersDao: UserRepository {

    // TODO change return type to Long? and save uid in sharedPref,
    //  such user already exists if return is null
    override suspend fun signUp(user: NewUser): Long? {
        val userEntity = mapNewUserToNewUserEntity(user)
        return createNewUser(userEntity)
    }

    override suspend fun getBalance(userId: Long): Double {
        return getBalanceById(userId = userId)
    }

    @Query("SELECT balance FROM users WHERE uid LIKE :userId LIMIT 1")
    fun getBalanceById(userId: Long): Double

    fun mapNewUserToNewUserEntity(user: NewUser): NewUserEntity {
        return NewUserEntity(
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            phone = user.phoneNumber,
            password = user.password,
            registrationDate = user.registrationDate
        )
    }

    @Insert
    fun createNewUser(user: NewUserEntity): Long?
}