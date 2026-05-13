package com.rai.accuratepeople.feature.users.data.local.mapper

import com.rai.accuratepeople.core.database.entity.CityEntity
import com.rai.accuratepeople.feature.users.domain.model.City

fun CityEntity.toDomain(): City = City(id = id, name = name)
