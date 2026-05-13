package com.rai.accuratepeople.feature.users.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.rai.accuratepeople.feature.users.data.analytics.UserAnalytics
import com.rai.accuratepeople.feature.users.domain.model.SortOrder
import com.rai.accuratepeople.feature.users.domain.model.User
import com.rai.accuratepeople.feature.users.domain.model.UserFilter
import com.rai.accuratepeople.feature.users.domain.usecase.GetCitiesUseCase
import com.rai.accuratepeople.feature.users.domain.usecase.GetUsersUseCase
import com.rai.accuratepeople.feature.users.domain.usecase.SyncCitiesUseCase
import com.rai.accuratepeople.feature.users.domain.usecase.SyncUsersUseCase
import com.rai.accuratepeople.feature.users.presentation.state.UserListUiState
import com.rai.accuratepeople.feature.users.worker.SyncWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val getCitiesUseCase: GetCitiesUseCase,
    private val syncUsersUseCase: SyncUsersUseCase,
    private val syncCitiesUseCase: SyncCitiesUseCase,
    private val analytics: UserAnalytics,
    private val workManager: WorkManager
) : ViewModel() {

    init {
        scheduleSync()
    }

    private val _filter       = MutableStateFlow(UserFilter())
    private val _isRefreshing = MutableStateFlow(false)

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private val usersFlow = _filter
        .debounce(300L)
        .flatMapLatest { filter -> getUsersUseCase(filter) }

    val uiState = combine(usersFlow, getCitiesUseCase(), _filter, _isRefreshing) { users, cities, filter, isRefreshing ->
        UserListUiState(
            users         = users,
            cities        = cities,
            searchQuery   = filter.searchQuery,
            selectedCity  = filter.selectedCity,
            sortOrder     = filter.sortOrder,
            isLoading     = false,
            isRefreshing  = isRefreshing
        )
    }.stateIn(
        scope        = viewModelScope,
        started      = SharingStarted.WhileSubscribed(5_000),
        initialValue = UserListUiState(isLoading = true)
    )

    fun onSearchQueryChanged(query: String) {
        _filter.update { it.copy(searchQuery = query) }
        if (query.isNotBlank()) analytics.logSearch(query.length)
    }

    fun onCitySelected(city: String) {
        _filter.update { it.copy(selectedCity = city) }
        if (city.isNotBlank()) analytics.logFilterByCity(city)
    }

    fun onSortToggled() {
        val newOrder = if (_filter.value.sortOrder == SortOrder.ASCENDING) SortOrder.DESCENDING else SortOrder.ASCENDING
        _filter.update { it.copy(sortOrder = newOrder) }
        analytics.logSortUsers(newOrder)
    }

    fun onUserSelected(user: User?) {
        // used for adaptive layout detail pane
    }

    fun onRefresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            syncCitiesUseCase()
            syncUsersUseCase()
            _isRefreshing.value = false
        }
    }

    fun scheduleSync() {
        val request = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            )
            .build()
        workManager.enqueueUniqueWork(
            "manual_sync",
            ExistingWorkPolicy.KEEP,
            request
        )
    }

    fun logScreenView() = analytics.logScreenView()

    fun logOpenAddUser() = analytics.logOpenAddUser()
}
