package com.im.dairyinventorymanagement.domain.usecase

import com.im.dairyinventorymanagement.data.model.request.LoginRequestData
import com.im.dairyinventorymanagement.data.model.response.LoginResponseData
import com.im.dairyinventorymanagement.domain.repository.Repository
import com.shubham.newsapiclientproject.data.util.Resource

class UserManagementUseCase(private val repository: Repository) {

    suspend fun loginUser(loginRequestData: LoginRequestData): Resource<LoginResponseData> {
        return repository.loginUser(loginRequestData)
    }

//    suspend fun registerUser(user: User) { ... }

//    suspend fun updateUser(user: User) { ... }
}