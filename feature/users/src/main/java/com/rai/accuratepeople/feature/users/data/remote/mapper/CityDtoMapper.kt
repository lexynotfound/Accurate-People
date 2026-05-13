package com.rai.accuratepeople.feature.users.data.remote.mapper

import com.rai.accuratepeople.core.database.entity.CityEntity
import com.rai.accuratepeople.feature.users.data.remote.dto.CityDto

fun CityDto.toEntity(): CityEntity = CityEntity(
    id   = id,
    name = name
)
