package com.rai.accuratepeople.feature.users.data.di

import com.rai.accuratepeople.feature.users.data.remote.api.CityApiService
import com.rai.accuratepeople.feature.users.data.remote.api.UserApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkServiceModule {

    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UserApiService =
        retrofit.create(UserApiService::class.java)

    @Provides
    @Singleton
    fun provideCityApiService(retrofit: Retrofit): CityApiService =
        retrofit.create(CityApiService::class.java)
}
