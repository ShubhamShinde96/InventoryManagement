package com.im.dairyinventorymanagement.domain.usecase

import com.im.dairyinventorymanagement.data.model.request.ModulesRequestData
import com.im.dairyinventorymanagement.data.model.response.ModulesResponseData
import com.im.dairyinventorymanagement.domain.repository.Repository
import com.shubham.newsapiclientproject.data.util.Resource

class ModulesManagementUseCase(private val repository: Repository) {

    suspend fun getModulesList(modulesRequestData: ModulesRequestData): Resource<List<ModulesResponseData>> {
        return repository.getModulesList(modulesRequestData)
    }

    suspend fun getSubModulesList(modulesRequestData: ModulesRequestData): Resource<List<ModulesResponseData>> {
        return repository.getSubModulesList(modulesRequestData)
    }

    suspend fun getMenuList(modulesRequestData: ModulesRequestData): Resource<List<ModulesResponseData>> {
        return repository.getMenuList(modulesRequestData)
    }
}