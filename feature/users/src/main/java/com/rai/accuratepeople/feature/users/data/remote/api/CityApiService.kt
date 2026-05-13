package com.rai.accuratepeople.feature.users.data.remote.api

import com.rai.accuratepeople.feature.users.data.remote.dto.CityDto
import retrofit2.http.GET

interface CityApiService {
    @GET("city")
    suspend fun getCities(): List<CityDto>
}
