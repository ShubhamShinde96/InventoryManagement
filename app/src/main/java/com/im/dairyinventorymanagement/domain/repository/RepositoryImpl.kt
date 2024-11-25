package com.im.dairyinventorymanagement.domain.repository

import com.im.dairyinventorymanagement.data.api.ApiService
import com.im.dairyinventorymanagement.data.model.request.LoginRequestData
import com.im.dairyinventorymanagement.data.model.response.LoginResponseData
import com.shubham.newsapiclientproject.data.util.Resource
import retrofit2.Response

class RepositoryImpl(private val apiService: ApiService) : Repository {

    override suspend fun loginUser(loginRequestData: LoginRequestData): Resource<List<LoginResponseData>> {
        return responseToResource(apiService.login(loginRequestData))
    }

    override suspend fun getModulesList(loginRequestData: LoginRequestData): Resource<List<LoginResponseData>> {
        TODO("Not yet implemented")
    }

    private fun responseToResource(response: Response<List<LoginResponseData>>): Resource<List<LoginResponseData>> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}
