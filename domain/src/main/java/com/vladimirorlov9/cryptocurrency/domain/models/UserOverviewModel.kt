package com.vladimirorlov9.cryptocurrency.domain.models

data class UserOverviewModel(
    val firstName: String,
    val lastName: String,
    val email: String,
    val uid: Int
)
