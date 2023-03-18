package com.vladimirorlov9.cryptocurrency.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.vladimirorlov9.cryptocurrency.data.storage.models.NewUserEntity
import com.vladimirorlov9.cryptocurrency.domain.constants.baseTradeName
import com.vladimirorlov9.cryptocurrency.domain.models.NewUser
import com.vladimirorlov9.cryptocurrency.domain.models.UserFullInfo
import com.vladimirorlov9.cryptocurrency.domain.models.UserOverviewModel
import com.vladimirorlov9.cryptocurrency.domain.repository.UserRepository

@Dao
interface UsersDao: UserRepository {

    override suspend fun updateUserInfo(
        uid: Int,
        firstName: String,
        lastName: String,
        email: String,
        tradeName: String,
        phoneNumber: String,
        birthday: Long
    ): UserOverviewModel {
        val user = getUser(uid)
        user.firstName = firstName
        user.lastName = lastName
        user.email = email
        user.tradeName = tradeName
        user.phone = phoneNumber
        user.birthday = birthday
        updateUserInfo(user)
        return getUserOverviewData(uid)
    }

    override suspend fun deleteProfilePicture(uid: Int): UserOverviewModel {
        val user = getUser(uid)
        user.image = null
        updateUserInfo(user)
        return getUserOverviewData(uid)
    }

    override suspend fun updateUserPicture(uid: Int, fileName: String): UserOverviewModel {
        val user = getUser(uid)
        user.image = fileName
        updateUserInfo(user)
        return getUserOverviewData(uid)
    }

    @Update(entity = NewUserEntity::class)
    fun updateUserInfo(user: NewUserEntity)

    override suspend fun getFullUserInfo(uid: Int): UserFullInfo {
        val user = getUser(uid)
        return user.mapToUserFullInfo()
    }

    fun NewUserEntity.mapToUserFullInfo(): UserFullInfo =
        UserFullInfo(
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email,
            phoneNumber = this.phone,
            password = this.password,
            registrationDate = this.registrationDate,
            image = this.image,
            tradeName = this.tradeName,
            birthday = this.birthday
        )

    override suspend fun getUserOverviewData(userId: Int): UserOverviewModel {
        val user = getUser(userId)

        return user.mapToUserOverviewModel()
    }

    fun NewUserEntity.mapToUserOverviewModel(): UserOverviewModel = UserOverviewModel(
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        uid = this.uid,
        image = this.image
    )

    @Query("SELECT * FROM users WHERE uid LIKE :userId LIMIT 1")
    fun getUser(userId: Int): NewUserEntity

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
            registrationDate = user.registrationDate,
            tradeName = "${baseTradeName}_${user.registrationDate}",
            birthday = user.registrationDate
        )
    }

    @Insert
    fun createNewUser(user: NewUserEntity): Long?
}