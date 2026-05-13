package com.rai.accuratepeople.feature.users.domain.repository

import com.rai.accuratepeople.core.common.result.Result
import com.rai.accuratepeople.feature.users.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun observeUsers(): Flow<List<User>>
    suspend fun addUser(user: User): Result<User>
    suspend fun syncUsers(): Result<Int>
}
