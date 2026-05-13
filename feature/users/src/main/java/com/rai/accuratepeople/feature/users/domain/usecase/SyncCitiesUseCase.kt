package com.rai.accuratepeople.feature.users.domain.usecase

import com.rai.accuratepeople.core.common.result.Result
import com.rai.accuratepeople.feature.users.domain.repository.CityRepository
import javax.inject.Inject

class SyncCitiesUseCase @Inject constructor(
    private val repository: CityRepository
) {
    suspend operator fun invoke(): Result<Int> = repository.syncCities()
}
