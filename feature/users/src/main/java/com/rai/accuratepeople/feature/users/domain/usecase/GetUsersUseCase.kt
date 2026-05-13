package com.rai.accuratepeople.feature.users.domain.usecase

import com.rai.accuratepeople.feature.users.domain.model.SortOrder
import com.rai.accuratepeople.feature.users.domain.model.User
import com.rai.accuratepeople.feature.users.domain.model.UserFilter
import com.rai.accuratepeople.feature.users.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(filter: UserFilter): Flow<List<User>> =
        repository.observeUsers().map { users ->
            users
                .filter { user ->
                    (filter.searchQuery.isBlank() ||
                     user.name.contains(filter.searchQuery, ignoreCase = true) ||
                     user.city.contains(filter.searchQuery, ignoreCase = true)) &&
                    (filter.selectedCity.isBlank() || user.city == filter.selectedCity)
                }
                .let { filtered ->
                    if (filter.sortOrder == SortOrder.ASCENDING) filtered.sortedBy { it.name }
                    else filtered.sortedByDescending { it.name }
                }
        }
}
