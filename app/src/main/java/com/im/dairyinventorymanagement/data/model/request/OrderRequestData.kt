package com.im.dairyinventorymanagement.data.model.request

data class SaveOrderRequestData(
    val authToken: String,
    val userId: String,
    val orderItems: List<OrderItems>
)

data class OrderItems(
    val id: String,
    val orderQuantity: String
)
