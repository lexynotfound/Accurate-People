package com.rai.accuratepeople.feature.users.presentation.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rai.accuratepeople.core.common.result.Result
import com.rai.accuratepeople.feature.users.data.analytics.UserAnalytics
import com.rai.accuratepeople.feature.users.domain.model.City
import com.rai.accuratepeople.feature.users.domain.model.User
import com.rai.accuratepeople.feature.users.domain.usecase.AddUserUseCase
import com.rai.accuratepeople.feature.users.domain.usecase.GetCitiesUseCase
import com.rai.accuratepeople.feature.users.presentation.state.AddUserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddUserViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase,
    private val getCitiesUseCase: GetCitiesUseCase,
    private val analytics: UserAnalytics
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddUserUiState())
    val uiState = _uiState.asStateFlow()

    val cities: StateFlow<List<City>> = getCitiesUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun onNameChanged(value: String)        = _uiState.update { it.copy(name = value, nameError = null) }
    fun onAddressChanged(value: String)     = _uiState.update { it.copy(address = value) }
    fun onEmailChanged(value: String)       = _uiState.update { it.copy(email = value, emailError = null) }
    fun onPhoneNumberChanged(value: String) = _uiState.update { it.copy(phoneNumber = value, phoneError = null) }
    fun onCityChanged(value: String)        = _uiState.update { it.copy(city = value, cityError = null) }
    fun onGenderChanged(value: Int)         = _uiState.update { it.copy(gender = value) }

    fun submitUser() {
        val state = _uiState.value

        val nameError  = validateName(state.name)
        val emailError = validateEmail(state.email)
        val phoneError = validatePhone(state.phoneNumber)
        val cityError  = validateCity(state.city)

        if (nameError != null || emailError != null || phoneError != null || cityError != null) {
            _uiState.update {
                it.copy(
                    nameError  = nameError,
                    emailError = emailError,
                    phoneError = phoneError,
                    cityError  = cityError
                )
            }
            return
        }

        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            val user = User(
                id          = "",
                name        = state.name.trim(),
                address     = state.address.trim(),
                email       = state.email.trim(),
                phoneNumber = state.phoneNumber.trim(),
                city        = state.city.trim(),
                gender      = state.gender
            )
            when (val result = addUserUseCase(user)) {
                is Result.Success -> {
                    analytics.logAddUserSuccess(state.city, state.gender)
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                }
                is Result.Error -> {
                    val msg = result.message ?: "Gagal menambahkan user"
                    analytics.logAddUserFailure(msg)
                    _uiState.update { it.copy(isLoading = false, errorMessage = msg) }
                }
                Result.Loading -> Unit
            }
        }
    }

    fun resetState() = _uiState.update { AddUserUiState() }

    private fun validateName(name: String): String? = when {
        name.isBlank()          -> "Nama wajib diisi"
        name.trim().length < 2  -> "Nama minimal 2 karakter"
        else                    -> null
    }

    private fun validateEmail(email: String): String? = when {
        email.isBlank()                                          -> "Email wajib diisi"
        !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> "Format email tidak valid"
        else                                                     -> null
    }

    private fun validatePhone(phone: String): String? {
        val digits = phone.filter { it.isDigit() }
        return when {
            phone.isBlank()  -> "No. telepon wajib diisi"
            digits.length < 8 -> "No. telepon minimal 8 digit"
            digits.length > 15 -> "No. telepon maksimal 15 digit"
            else             -> null
        }
    }

    private fun validateCity(city: String): String? = when {
        city.isBlank() -> "Kota wajib dipilih"
        else           -> null
    }
}
