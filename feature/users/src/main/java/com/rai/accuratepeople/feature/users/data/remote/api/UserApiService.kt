package com.rai.accuratepeople.feature.users.data.remote.api

import com.rai.accuratepeople.feature.users.data.remote.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApiService {
    @GET("user")
    suspend fun getUsers(): List<UserDto>

    @POST("user")
    suspend fun addUser(@Body user: UserDto): UserDto
}
