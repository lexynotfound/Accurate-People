package com.rai.accuratepeople.feature.users.domain.usecase

import com.rai.accuratepeople.core.common.result.Result
import com.rai.accuratepeople.feature.users.domain.repository.UserRepository
import javax.inject.Inject

class SyncUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): Result<Int> = repository.syncUsers()
}
