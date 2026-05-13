package com.rai.accuratepeople.feature.users.domain.usecase

import com.rai.accuratepeople.core.common.result.Result
import com.rai.accuratepeople.feature.users.domain.model.User
import com.rai.accuratepeople.feature.users.domain.repository.UserRepository
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User): Result<User> = repository.addUser(user)
}
