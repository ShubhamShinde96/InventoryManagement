package com.im.dairyinventorymanagement.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.im.dairyinventorymanagement.domain.usecase.OrderManagementUseCase

class OrderViewModelFactory(
    private val application: Application,
    private val orderManagementUseCase: OrderManagementUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        OrderViewModel(application, orderManagementUseCase) as T
}
