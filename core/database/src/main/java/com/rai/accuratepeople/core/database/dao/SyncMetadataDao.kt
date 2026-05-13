package com.rai.accuratepeople.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rai.accuratepeople.core.database.entity.SyncMetadataEntity

@Dao
interface SyncMetadataDao {

    @Query("SELECT lastSyncTimestamp FROM sync_metadata WHERE `key` = :key")
    suspend fun getLastSyncTimestamp(key: String): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(metadata: SyncMetadataEntity)
}
