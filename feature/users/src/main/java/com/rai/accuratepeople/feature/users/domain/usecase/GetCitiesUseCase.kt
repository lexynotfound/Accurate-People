package com.rai.accuratepeople.feature.users.domain.usecase

import com.rai.accuratepeople.feature.users.domain.model.City
import com.rai.accuratepeople.feature.users.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(
    private val repository: CityRepository
) {
    operator fun invoke(): Flow<List<City>> = repository.observeCities()
}
