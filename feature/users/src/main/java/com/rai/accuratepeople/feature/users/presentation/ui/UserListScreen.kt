package com.rai.accuratepeople.feature.users.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.rai.accuratepeople.core.ui.adaptive.isExpandedLayout
import com.rai.accuratepeople.core.ui.component.LoadingIndicator
import com.rai.accuratepeople.feature.users.domain.model.User
import com.rai.accuratepeople.feature.users.presentation.viewmodel.UserListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    viewModel: UserListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val isExpanded = isExpandedLayout()

    var showAddUser by rememberSaveable { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(Unit) { viewModel.logScreenView() }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Accurate People") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.logOpenAddUser()
                showAddUser = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Tambah User")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            when {
                uiState.isLoading -> LoadingIndicator()
                isExpanded -> {
                    Row(modifier = Modifier.fillMaxSize()) {
                        UserListPane(
                            users           = uiState.users,
                            cities          = uiState.cities,
                            searchQuery     = uiState.searchQuery,
                            selectedCity    = uiState.selectedCity,
                            sortOrder       = uiState.sortOrder,
                            isRefreshing    = uiState.isRefreshing,
                            onSearchChanged = viewModel::onSearchQueryChanged,
                            onCitySelected  = viewModel::onCitySelected,
                            onSortToggled   = viewModel::onSortToggled,
                            onRefresh       = viewModel::onRefresh,
                            onUserClick     = { user -> selectedUser = user },
                            modifier        = Modifier.weight(1f)
                        )
                        UserDetailPane(
                            user     = selectedUser,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                selectedUser != null -> {
                    UserDetailPane(
                        user     = selectedUser,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                else -> {
                    UserListPane(
                        users           = uiState.users,
                        cities          = uiState.cities,
                        searchQuery     = uiState.searchQuery,
                        selectedCity    = uiState.selectedCity,
                        sortOrder       = uiState.sortOrder,
                        isRefreshing    = uiState.isRefreshing,
                        onSearchChanged = viewModel::onSearchQueryChanged,
                        onCitySelected  = viewModel::onCitySelected,
                        onSortToggled   = viewModel::onSortToggled,
                        onRefresh       = viewModel::onRefresh,
                        onUserClick     = { user -> selectedUser = user },
                        modifier        = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }

    if (showAddUser) {
        AddUserScreen(onDismiss = { showAddUser = false })
    }
}
