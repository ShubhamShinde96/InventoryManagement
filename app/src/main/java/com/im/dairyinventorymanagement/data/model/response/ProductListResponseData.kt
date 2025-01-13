package com.im.dairyinventorymanagement.data.model.response

data class ProductListResponseData(
    val status: String,
    val data: List<Module>
)

data class Product(
    val id: String,
    val title: String,
    val availableqty: Int,
    var orderQty: Int = 0
)
