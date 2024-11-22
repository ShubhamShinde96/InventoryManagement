package com.im.dairyinventorymanagement.presentation.di

import android.app.Application
import com.im.dairyinventorymanagement.domain.usecase.UserManagementUseCase
import com.im.dairyinventorymanagement.presentation.viewmodel.HostViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun providesHostViewModelFactory(
        application: Application,
        userManagementUseCase: UserManagementUseCase
    ): HostViewModelFactory {
        return HostViewModelFactory(
            application,
            userManagementUseCase
        )
    }
}