package com.im.dairyinventorymanagement.domain.repository

import com.google.gson.Gson
import com.im.dairyinventorymanagement.data.api.ApiService
import com.im.dairyinventorymanagement.data.model.request.SaveOrderRequestData
import com.im.dairyinventorymanagement.data.model.response.SaveOrderResponseData
import com.shubham.newsapiclientproject.data.util.Resource
import retrofit2.Response

class OrderRepositoryImpl(private val apiService: ApiService): OrderRepository {

    override suspend fun saveOrder(saveOrderRequestData: SaveOrderRequestData): Resource<List<SaveOrderResponseData>> {
        return responseToResource(apiService.saveOrder(saveOrderRequestData))
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
}
