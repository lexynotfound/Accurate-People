package com.rai.accuratepeople.feature.users.data.repository

import com.rai.accuratepeople.core.common.result.Result
import com.rai.accuratepeople.core.database.dao.UserDao
import com.rai.accuratepeople.feature.users.data.local.mapper.toDomain
import com.rai.accuratepeople.feature.users.data.local.mapper.toEntity
import com.rai.accuratepeople.feature.users.data.remote.api.UserApiService
import com.rai.accuratepeople.feature.users.data.remote.mapper.toDto
import com.rai.accuratepeople.feature.users.data.remote.mapper.toEntity
import com.rai.accuratepeople.feature.users.domain.model.User
import com.rai.accuratepeople.feature.users.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: UserApiService,
    private val userDao: UserDao
) : UserRepository {

    override fun observeUsers(): Flow<List<User>> =
        userDao.observeAllUsers().map { entities -> entities.map { it.toDomain() } }

    override suspend fun addUser(user: User): Result<User> = try {
        val dto = apiService.addUser(user.toDto())
        val entity = dto.toEntity()
        userDao.insertOne(entity)
        Result.Success(entity.toDomain())
    } catch (e: Exception) {
        Result.Error(e, e.message)
    }

    override suspend fun syncUsers(): Result<Int> = try {
        val dtos = apiService.getUsers()
        val entities = dtos.map { it.toEntity() }
        userDao.replaceAll(entities)
        Result.Success(entities.size)
    } catch (e: Exception) {
        Result.Error(e, e.message)
    }
}
