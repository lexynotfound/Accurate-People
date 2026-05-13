package com.rai.accuratepeople.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.rai.accuratepeople.core.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM users ORDER BY name ASC")
    fun observeAllUsers(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchUsers(query: String): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE city = :city ORDER BY name ASC")
    fun filterByCity(city: String): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE name LIKE '%' || :query || '%' AND city = :city ORDER BY name ASC")
    fun searchAndFilter(query: String, city: String): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(user: UserEntity)

    @Query("DELETE FROM users")
    suspend fun deleteAll()

    @Transaction
    suspend fun replaceAll(users: List<UserEntity>) {
        deleteAll()
        insertAll(users)
    }
}
