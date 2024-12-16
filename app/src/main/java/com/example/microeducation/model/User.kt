package com.example.microeducation.model

import com.google.gson.annotations.SerializedName

data class User (
    val id: Int,
    val name: String,
    val mail: String,
    @SerializedName("itmo_Id") val itmoId: Int,
    @SerializedName("password_HK") val password: String,
    val createdAt: String
)