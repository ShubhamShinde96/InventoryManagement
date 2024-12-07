package com.im.dairyinventorymanagement.data.model.request

data class ModulesRequestData(
    val authToken: String,
    val userId: String,
    val moduleId: String? = null,
    val subModuleId: String? = null
)