package com.rai.accuratepeople.feature.users.domain.repository

import com.rai.accuratepeople.core.common.result.Result
import com.rai.accuratepeople.feature.users.domain.model.City
import kotlinx.coroutines.flow.Flow

interface CityRepository {
    fun observeCities(): Flow<List<City>>
    suspend fun syncCities(): Result<Int>
}
