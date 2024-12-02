package com.im.dairyinventorymanagement.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.im.dairyinventorymanagement.domain.usecase.ModulesManagementUseCase
import com.im.dairyinventorymanagement.domain.usecase.UserManagementUseCase

class HostViewModelFactory(
    private val application: Application,
    private val userManagementUseCase: UserManagementUseCase,
    private val modulesManagementUseCase: ModulesManagementUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras) =
        HostViewModel(application, userManagementUseCase, modulesManagementUseCase) as T
}
