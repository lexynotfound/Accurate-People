package com.rai.accuratepeople.core.common.di

import com.rai.accuratepeople.core.common.dispatcher.DispatcherProvider
import com.rai.accuratepeople.core.common.dispatcher.DispatcherProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatcherModule {

    @Binds
    @Singleton
    abstract fun bindDispatcherProvider(impl: DispatcherProviderImpl): DispatcherProvider
}
