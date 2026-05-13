package com.rai.accuratepeople.feature.users.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.rai.accuratepeople.feature.users.domain.model.City
import com.rai.accuratepeople.feature.users.presentation.viewmodel.AddUserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUserScreen(
    onDismiss: () -> Unit,
    viewModel: AddUserViewModel = hiltViewModel(
        checkNotNull(LocalViewModelStoreOwner.current) {
            "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
        }, null
    )
) {
    val state by viewModel.uiState.collectAsState()
    val cities by viewModel.cities.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            viewModel.resetState()
            onDismiss()
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
        ) {
            Text(
                text  = "Tambah User Baru",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            FormField(
                label         = "Nama *",
                value         = state.name,
                onValueChange = viewModel::onNameChanged,
                keyboardOptions = KeyboardOptions(
                    keyboardType    = KeyboardType.Text,
                    capitalization  = KeyboardCapitalization.Words
                ),
                isError       = state.nameError != null,
                errorMessage  = state.nameError
            )

            FormField(
                label         = "Email *",
                value         = state.email,
                onValueChange = viewModel::onEmailChanged,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError       = state.emailError != null,
                errorMessage  = state.emailError
            )

            FormField(
                label         = "No. Telepon *",
                value         = state.phoneNumber,
                onValueChange = viewModel::onPhoneNumberChanged,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                isError       = state.phoneError != null,
                errorMessage  = state.phoneError
            )

            FormField(
                label         = "Alamat",
                value         = state.address,
                onValueChange = viewModel::onAddressChanged,
                keyboardOptions = KeyboardOptions(
                    keyboardType   = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Sentences
                )
            )

            CityAutocompleteField(
                query          = state.city,
                cities         = cities,
                isError        = state.cityError != null,
                errorMessage   = state.cityError,
                onCitySelected = viewModel::onCityChanged
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(text = "Gender", style = MaterialTheme.typography.labelLarge)
            Row(modifier = Modifier.padding(vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = state.gender == 0,
                    onClick  = { viewModel.onGenderChanged(0) },
                    label    = { Text("Perempuan") }
                )
                FilterChip(
                    selected = state.gender == 1,
                    onClick  = { viewModel.onGenderChanged(1) },
                    label    = { Text("Laki-laki") }
                )
            }

            state.errorMessage?.let { error ->
                Text(
                    text  = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Button(
                onClick  = viewModel::submitUser,
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                enabled  = !state.isLoading
            ) {
                if (state.isLoading) CircularProgressIndicator()
                else Text("Simpan")
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CityAutocompleteField(
    query: String,
    cities: List<City>,
    isError: Boolean = false,
    errorMessage: String? = null,
    onCitySelected: (String) -> Unit
) {
    val filtered = remember(query, cities) {
        if (query.isBlank()) cities
        else cities.filter { it.name.contains(query, ignoreCase = true) }
    }
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded && filtered.isNotEmpty(),
        onExpandedChange = { if (it) expanded = true },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = if (errorMessage != null) 2.dp else 12.dp)
    ) {
        OutlinedTextField(
            value         = query,
            onValueChange = {
                onCitySelected(it)
                expanded = true
            },
            label         = { Text("Kota *") },
            modifier      = Modifier
                .fillMaxWidth()
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable),
            singleLine    = true,
            isError       = isError,
            supportingText = errorMessage?.let { msg -> { Text(msg) } }
        )
        ExposedDropdownMenu(
            expanded         = expanded && filtered.isNotEmpty(),
            onDismissRequest = { expanded = false }
        ) {
            filtered.forEach { city ->
                DropdownMenuItem(
                    text    = { Text(city.name) },
                    onClick = {
                        onCitySelected(city.name)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun FormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    OutlinedTextField(
        value          = value,
        onValueChange  = onValueChange,
        label          = { Text(label) },
        modifier       = Modifier
            .fillMaxWidth()
            .padding(bottom = if (errorMessage != null) 2.dp else 12.dp),
        singleLine     = true,
        keyboardOptions = keyboardOptions,
        isError        = isError,
        supportingText = errorMessage?.let { msg -> { Text(msg) } }
    )
}
