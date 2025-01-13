package com.im.dairyinventorymanagement.data.model.response

data class SaveOrderResponseData(
    val status: String,
    val data: SavedItem
)

data class SavedItem(
    val orderNumber: String,
    val data: List<OrderDetails>
)

data class OrderDetails(
    val id: String,
    val orderQty: String,
    val rate: String,
    val BasicAmt: Double,
    val disAmt: Double,
    val accessVal: Double,
    val cgstAmt: Double,
    val sgstAmt: Double,
    val igstAmt: Double,
    val netAmt: Double
)
