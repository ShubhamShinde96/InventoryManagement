package com.im.dairyinventorymanagement.data.model.response

data class ModulesResponseData(
    val id: Int,
    val title: String,
    val description: String,
    val image: Int,
    val backgroundColor: Int,
    val navigationActionRouteName: String
)
