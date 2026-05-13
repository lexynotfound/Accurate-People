package com.rai.accuratepeople.feature.users.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.rai.accuratepeople.core.ui.component.EmptyView
import com.rai.accuratepeople.core.ui.component.SortToggleButton
import com.rai.accuratepeople.core.ui.component.UserSearchBar
import com.rai.accuratepeople.feature.users.domain.model.City
import com.rai.accuratepeople.feature.users.domain.model.SortOrder
import com.rai.accuratepeople.feature.users.domain.model.User
import com.rai.accuratepeople.feature.users.presentation.ui.components.FilterChipRow
import com.rai.accuratepeople.feature.users.presentation.ui.components.UserCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListPane(
    users: List<User>,
    cities: List<City>,
    searchQuery: String,
    selectedCity: String,
    sortOrder: SortOrder,
    isRefreshing: Boolean,
    onSearchChanged: (String) -> Unit,
    onCitySelected: (String) -> Unit,
    onSortToggled: () -> Unit,
    onRefresh: () -> Unit,
    onUserClick: (User) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            UserSearchBar(
                query         = searchQuery,
                onQueryChange = onSearchChanged,
                modifier      = Modifier.weight(1f)
            )
            SortToggleButton(
                isAscending = sortOrder == SortOrder.ASCENDING,
                onToggle    = onSortToggled
            )
        }

        FilterChipRow(
            cities         = cities,
            selectedCity   = selectedCity,
            onCitySelected = onCitySelected
        )

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh    = onRefresh,
            modifier     = Modifier.fillMaxSize()
        ) {
            if (users.isEmpty()) {
                EmptyView(message = "Tidak ada user ditemukan")
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(users, key = { it.id }) { user ->
                        UserCard(user = user, onClick = onUserClick)
                    }
                }
            }
        }
    }
}
