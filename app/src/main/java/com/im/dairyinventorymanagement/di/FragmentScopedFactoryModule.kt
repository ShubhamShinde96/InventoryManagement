package com.im.dairyinventorymanagement.di

import android.app.Application
import com.im.dairyinventorymanagement.domain.usecase.OrderManagementUseCase
import com.im.dairyinventorymanagement.presentation.viewmodel.OrderViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class FragmentScopedFactoryModule {

    @Provides
    @FragmentScoped
    fun providesOrderManagementViewModelFactory(
        application: Application, orderManagementUseCase: OrderManagementUseCase
    ) = OrderViewModelFactory(application, orderManagementUseCase)
}