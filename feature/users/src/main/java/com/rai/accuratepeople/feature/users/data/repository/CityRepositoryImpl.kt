package com.rai.accuratepeople.feature.users.data.repository

import com.rai.accuratepeople.core.common.result.Result
import com.rai.accuratepeople.core.database.dao.CityDao
import com.rai.accuratepeople.feature.users.data.local.mapper.toDomain
import com.rai.accuratepeople.feature.users.data.remote.api.CityApiService
import com.rai.accuratepeople.feature.users.data.remote.mapper.toEntity
import com.rai.accuratepeople.feature.users.domain.model.City
import com.rai.accuratepeople.feature.users.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val apiService: CityApiService,
    private val cityDao: CityDao
) : CityRepository {

    override fun observeCities(): Flow<List<City>> =
        cityDao.observeAllCities().map { entities -> entities.map { it.toDomain() } }

    override suspend fun syncCities(): Result<Int> = try {
        val dtos = apiService.getCities()
        val entities = dtos.map { it.toEntity() }
        cityDao.replaceAll(entities)
        Result.Success(entities.size)
    } catch (e: Exception) {
        Result.Error(e, e.message)
    }
}
