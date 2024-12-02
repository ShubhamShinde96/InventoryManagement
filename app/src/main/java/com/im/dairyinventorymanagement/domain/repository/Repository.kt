package com.im.dairyinventorymanagement.domain.repository

import com.im.dairyinventorymanagement.data.model.request.LoginRequestData
import com.im.dairyinventorymanagement.data.model.request.ModulesRequestData
import com.im.dairyinventorymanagement.data.model.response.LoginResponseData
import com.im.dairyinventorymanagement.data.model.response.ModulesResponseData
import com.shubham.newsapiclientproject.data.util.Resource

interface Repository {

    suspend fun loginUser(loginRequestData: LoginRequestData): Resource<List<LoginResponseData>>

    suspend fun getModulesList(modulesRequestData: ModulesRequestData): Resource<List<ModulesResponseData>>
}
