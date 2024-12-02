package com.im.dairyinventorymanagement.data.model.response

data class ModulesResponseData(
    val status: String,
    val data: List<Module>
)

data class Module(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val navigationActionRouteName: String
)
