package com.vladimirorlov9.cryptocurrency.domain.models

data class UserFullInfo(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val password: String,
    val registrationDate: Long,
    val image: String?,
    val tradeName: String,
    val birthday: Long
)
