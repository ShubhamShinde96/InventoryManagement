package com.im.dairyinventorymanagement.data.api

import com.im.dairyinventorymanagement.data.model.request.LoginRequestData
import com.im.dairyinventorymanagement.data.model.request.ModulesRequestData
import com.im.dairyinventorymanagement.data.model.response.LoginResponseData
import com.im.dairyinventorymanagement.data.model.response.ModulesResponseData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("login.php")
    suspend fun login(@Body request: LoginRequestData): Response<List<LoginResponseData>>

    @POST("getModule.php")
    suspend fun getModulesList(@Body request: ModulesRequestData): Response<List<ModulesResponseData>>
}
