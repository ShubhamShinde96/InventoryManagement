package com.im.dairyinventorymanagement.domain.repository

import com.im.dairyinventorymanagement.data.model.request.LoginRequestData
import com.im.dairyinventorymanagement.data.model.response.LoginResponseData
import com.shubham.newsapiclientproject.data.util.Resource

interface Repository {

    suspend fun loginUser(loginRequestData: LoginRequestData): Resource<List<LoginResponseData>>
}
