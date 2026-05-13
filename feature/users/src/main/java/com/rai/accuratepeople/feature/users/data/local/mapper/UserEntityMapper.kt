package com.rai.accuratepeople.feature.users.data.local.mapper

import com.rai.accuratepeople.core.database.entity.UserEntity
import com.rai.accuratepeople.feature.users.domain.model.User

fun UserEntity.toDomain(): User = User(
    id          = id,
    name        = name,
    address     = address,
    email       = email,
    phoneNumber = phoneNumber,
    city        = city,
    gender      = gender
)

fun User.toEntity(): UserEntity = UserEntity(
    id          = id,
    name        = name,
    address     = address,
    email       = email,
    phoneNumber = phoneNumber,
    city        = city,
    gender      = gender
)
