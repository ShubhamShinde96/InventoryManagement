package com.im.dairyinventorymanagement.domain.repository

import com.im.dairyinventorymanagement.data.model.request.SaveOrderRequestData
import com.im.dairyinventorymanagement.data.model.response.SaveOrderResponseData
import com.shubham.newsapiclientproject.data.util.Resource

interface OrderRepository {

    suspend fun saveOrder(saveOrderRequestData: SaveOrderRequestData): Resource<List<SaveOrderResponseData>>
}
