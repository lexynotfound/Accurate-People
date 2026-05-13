package com.rai.accuratepeople.feature.users.presentation.state

import com.rai.accuratepeople.feature.users.domain.model.City
import com.rai.accuratepeople.feature.users.domain.model.SortOrder
import com.rai.accuratepeople.feature.users.domain.model.User

data class UserListUiState(
    val users: List<User>       = emptyList(),
    val cities: List<City>      = emptyList(),
    val searchQuery: String     = "",
    val selectedCity: String    = "",
    val sortOrder: SortOrder    = SortOrder.ASCENDING,
    val isLoading: Boolean      = true,
    val isRefreshing: Boolean   = false,
    val errorMessage: String?   = null,
    val selectedUser: User?     = null
)
