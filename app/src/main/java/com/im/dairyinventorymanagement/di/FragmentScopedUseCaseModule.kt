package com.im.dairyinventorymanagement.di

import com.im.dairyinventorymanagement.domain.repository.OrderRepository
import com.im.dairyinventorymanagement.domain.usecase.OrderManagementUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class FragmentScopedUseCaseModule {

    @Provides
    @FragmentScoped
    fun provideOrderManagementUseCase(
        orderRepository: OrderRepository
    ): OrderManagementUseCase {
        return OrderManagementUseCase(orderRepository)
    }
}
