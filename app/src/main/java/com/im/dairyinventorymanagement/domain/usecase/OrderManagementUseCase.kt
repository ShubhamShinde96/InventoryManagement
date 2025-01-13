package com.im.dairyinventorymanagement.domain.usecase

import com.im.dairyinventorymanagement.data.model.request.SaveOrderRequestData
import com.im.dairyinventorymanagement.data.model.response.SaveOrderResponseData
import com.im.dairyinventorymanagement.domain.repository.OrderRepository
import com.shubham.newsapiclientproject.data.util.Resource

class OrderManagementUseCase(private val orderRepository: OrderRepository) {

    suspend fun saveOrder(saveOrderRequestData: SaveOrderRequestData): Resource<List<SaveOrderResponseData>> {
        return orderRepository.saveOrder(saveOrderRequestData)
    }
}
