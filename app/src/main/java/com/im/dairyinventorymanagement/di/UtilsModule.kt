package com.im.dairyinventorymanagement.di

import android.content.Context
import com.im.dairyinventorymanagement.utils.SharedPreferencesHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {

    @Singleton
    @Provides
    fun providesSharedPreferencesHandler(
        @ApplicationContext context: Context
    ): SharedPreferencesHandler {
        return SharedPreferencesHandler(context)
    }

}
