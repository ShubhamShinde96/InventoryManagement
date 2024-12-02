package com.im.dairyinventorymanagement.domain.repository

import com.google.gson.Gson
import com.im.dairyinventorymanagement.data.api.ApiService
import com.im.dairyinventorymanagement.data.model.request.LoginRequestData
import com.im.dairyinventorymanagement.data.model.request.ModulesRequestData
import com.im.dairyinventorymanagement.data.model.response.LoginResponseData
import com.im.dairyinventorymanagement.data.model.response.ModulesResponseData
import com.shubham.newsapiclientproject.data.util.Resource
import retrofit2.Response

class RepositoryImpl(private val apiService: ApiService) : Repository {

    override suspend fun loginUser(loginRequestData: LoginRequestData): Resource<List<LoginResponseData>> {
        return responseToResource(apiService.login(loginRequestData))
    }

    override suspend fun getModulesList(modulesRequestData: ModulesRequestData): Resource<List<ModulesResponseData>> {
        return responseToResource(apiService.getModulesList(modulesRequestData))
    }

    private inline fun <reified T> responseToResource(response: Response<T>): Resource<T> {
        return if (response.isSuccessful) {
            response.body()?.let { Resource.Success(it) } ?: Resource.Error("Response body is null")
        } else {
            try {
                val errorResponse = Gson().fromJson(response.errorBody()?.string(), T::class.java)
                Resource.Error(response.message(), errorResponse)
            } catch (e: Exception) {
                Resource.Error(response.message())
            }
        }
    }

    /*private fun responseToResource(response: Response<List<LoginResponseData>>): Resource<List<LoginResponseData>> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }*/

    /*inline fun <reified T> Resource<T>.toDataClass(gson: Gson): T? {
        return when (this) {
            is Resource.Success -> {
                data // Data is already of type T in Success case
            }
            is Resource.Loading -> {
                data // Data might be null or of type T in Loading case
            }
            is Resource.Error -> {
                // Attempt to parse error response to your data class
                message?.let {
                    try {
                        gson.fromJson(it, T::class.java)
                    } catch (e: JsonSyntaxException) {
                        null // Handle parsing error, e.g., log or throw a custom exception
                    }
                }
            }
        }
    }*/
}
