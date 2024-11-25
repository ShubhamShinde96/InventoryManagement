package com.im.dairyinventorymanagement.di

import com.im.dairyinventorymanagement.data.api.ApiService
import com.im.dairyinventorymanagement.domain.repository.Repository
import com.im.dairyinventorymanagement.domain.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesRepository(
        apiService: ApiService
    ): Repository {
        return RepositoryImpl(apiService)
    }
}