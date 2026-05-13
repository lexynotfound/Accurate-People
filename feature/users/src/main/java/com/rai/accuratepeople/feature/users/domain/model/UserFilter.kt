package com.rai.accuratepeople.feature.users.domain.model

data class UserFilter(
    val searchQuery: String = "",
    val selectedCity: String = "",
    val sortOrder: SortOrder = SortOrder.ASCENDING
)
