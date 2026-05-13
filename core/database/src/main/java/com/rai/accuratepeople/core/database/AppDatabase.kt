package com.rai.accuratepeople.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rai.accuratepeople.core.database.dao.CityDao
import com.rai.accuratepeople.core.database.dao.SyncMetadataDao
import com.rai.accuratepeople.core.database.dao.UserDao
import com.rai.accuratepeople.core.database.entity.CityEntity
import com.rai.accuratepeople.core.database.entity.SyncMetadataEntity
import com.rai.accuratepeople.core.database.entity.UserEntity

@Database(
    entities = [UserEntity::class, CityEntity::class, SyncMetadataEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun cityDao(): CityDao
    abstract fun syncMetadataDao(): SyncMetadataDao

    companion object {
        const val DATABASE_NAME = "accurate_people.db"
    }
}
