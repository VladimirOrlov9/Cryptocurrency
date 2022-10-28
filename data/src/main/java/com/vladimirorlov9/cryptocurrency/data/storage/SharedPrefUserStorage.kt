package com.vladimirorlov9.cryptocurrency.data.storage

import android.content.Context
import com.vladimirorlov9.cryptocurrency.data.storage.models.User

const val SHARED_PREFERENCES_USER = "user_preferences"
const val KEY_USER_NAME = "userName"
const val DEFAULT_USER_NAME = "def"

class SharedPrefUserStorage(context: Context): UserStorageInterface {

    private val sharedPreferences = context.getSharedPreferences(
        SHARED_PREFERENCES_USER,
        Context.MODE_PRIVATE
    )

    override fun save(user: User) {
        sharedPreferences.edit()
            .putString(KEY_USER_NAME, user.name)
            .apply()
    }

    override fun get(): User {
        val name = sharedPreferences.getString(KEY_USER_NAME, DEFAULT_USER_NAME) ?: DEFAULT_USER_NAME

        return User(name = name)
    }
}