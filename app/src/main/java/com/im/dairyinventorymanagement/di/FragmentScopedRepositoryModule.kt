package com.im.dairyinventorymanagement.di

import com.im.dairyinventorymanagement.data.api.ApiService
import com.im.dairyinventorymanagement.domain.repository.OrderRepository
import com.im.dairyinventorymanagement.domain.repository.OrderRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class FragmentScopedRepositoryModule {

    @Provides
    @FragmentScoped
    fun provideOrderRepository(
        apiService: ApiService
    ): OrderRepository {
        return OrderRepositoryImpl(apiService)
    }
}
