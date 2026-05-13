package com.rai.accuratepeople.feature.users.presentation.state

data class AddUserUiState(
    val name: String          = "",
    val address: String       = "",
    val email: String         = "",
    val phoneNumber: String   = "",
    val city: String          = "",
    val gender: Int           = 0,
    val isLoading: Boolean    = false,
    val isSuccess: Boolean    = false,
    val errorMessage: String? = null,
    val nameError: String?    = null,
    val emailError: String?   = null,
    val phoneError: String?   = null,
    val cityError: String?    = null
)
