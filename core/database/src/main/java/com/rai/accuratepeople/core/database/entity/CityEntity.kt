package com.rai.accuratepeople.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey val id: String,
    val name: String,
    val syncedAt: Long = System.currentTimeMillis()
)
