package com.example.microeducation.network

import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class RegisterRequest(
    val name: String,
    val pass: String,
    val email: String
)

data class LoginRequest(
    @SerializedName("name") val name: String,
    @SerializedName("pass") val pass: String
)

data class AuthResponse(
    @SerializedName("token") val token: String
)

data class Module(
    val id: Int,
    val name: String,
    val videoLink: String,
    val courseId: Int,
    val course: Course
)

data class Course(
    val id: Int,
    val name: String,
    val mail: String,
    val duration: String,
    val description: String
)


interface ApiService {
    @POST("api/Auth/register")
    fun registerUser(@Body request: RegisterRequest): Call<Void>

    @POST("api/Auth/login")
    fun loginUser(@Body request: LoginRequest): Call<AuthResponse>

    @POST("api/Module/GetModules")
    fun getModules(@Body courseName: String): Call<List<Module>>
}