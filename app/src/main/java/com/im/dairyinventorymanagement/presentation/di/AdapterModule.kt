package com.im.dairyinventorymanagement.presentation.di

import com.im.dairyinventorymanagement.presentation.adapter.ModulesListAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Singleton
    @Provides
    fun providesAdapter() = ModulesListAdapter()
}