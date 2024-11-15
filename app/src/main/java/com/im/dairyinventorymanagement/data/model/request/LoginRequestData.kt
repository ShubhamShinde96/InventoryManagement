package com.im.dairyinventorymanagement.data.model.request

data class LoginRequestData(val credentials: Credentials)

data class Credentials(val username: String, val password: String)
