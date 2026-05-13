package com.rai.accuratepeople.feature.users.data.di

import com.rai.accuratepeople.feature.users.data.repository.CityRepositoryImpl
import com.rai.accuratepeople.feature.users.data.repository.UserRepositoryImpl
import com.rai.accuratepeople.feature.users.domain.repository.CityRepository
import com.rai.accuratepeople.feature.users.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserDataModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindCityRepository(impl: CityRepositoryImpl): CityRepository
}
