package com.rai.accuratepeople.feature.users.data.remote.mapper

import com.rai.accuratepeople.core.database.entity.UserEntity
import com.rai.accuratepeople.feature.users.data.remote.dto.UserDto
import com.rai.accuratepeople.feature.users.domain.model.User

fun UserDto.toEntity(): UserEntity = UserEntity(
    id          = id ?: System.currentTimeMillis().toString(),
    name        = name,
    address     = address,
    email       = email,
    phoneNumber = phoneNumber,
    city        = city,
    gender      = gender
)

fun User.toDto(): UserDto = UserDto(
    id          = if (id.isEmpty()) null else id,
    name        = name,
    address     = address,
    email       = email,
    phoneNumber = phoneNumber,
    city        = city,
    gender      = gender
)
