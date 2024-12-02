package com.im.dairyinventorymanagement.data.model.response

data class LoginResponseData(
    val status: String,
    val data: UserData
)

data class UserData(
    val token: String,
    val user: User
)

data class User(
    val id: String,
    val username: String,
    val email: String,
    val name: String
)
