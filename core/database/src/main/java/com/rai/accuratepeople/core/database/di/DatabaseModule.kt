package com.rai.accuratepeople.core.database.di

import android.content.Context
import androidx.room.Room
import com.rai.accuratepeople.core.database.AppDatabase
import com.rai.accuratepeople.core.database.dao.CityDao
import com.rai.accuratepeople.core.database.dao.SyncMetadataDao
import com.rai.accuratepeople.core.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .build()

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    fun provideCityDao(db: AppDatabase): CityDao = db.cityDao()

    @Provides
    fun provideSyncMetadataDao(db: AppDatabase): SyncMetadataDao = db.syncMetadataDao()
}
