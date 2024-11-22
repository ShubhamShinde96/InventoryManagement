package com.im.dairyinventorymanagement.presentation.di

import com.im.dairyinventorymanagement.domain.repository.Repository
import com.im.dairyinventorymanagement.domain.usecase.UserManagementUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun providesUserManagementUseCase(
        repositoryImpl: Repository
    ): UserManagementUseCase {
        return UserManagementUseCase(repositoryImpl)
    }
}