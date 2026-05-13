package com.rai.accuratepeople.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.rai.accuratepeople.core.database.entity.CityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {

    @Query("SELECT * FROM cities ORDER BY name ASC")
    fun observeAllCities(): Flow<List<CityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cities: List<CityEntity>)

    @Query("DELETE FROM cities")
    suspend fun deleteAll()

    @Transaction
    suspend fun replaceAll(cities: List<CityEntity>) {
        deleteAll()
        insertAll(cities)
    }
}
