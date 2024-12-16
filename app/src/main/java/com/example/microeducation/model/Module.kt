package com.example.microeducation.model

import com.google.gson.annotations.SerializedName

data class Module(
    val id: Int,
    val name: String,
    @SerializedName("video_Link") val videoLink: String,
    @SerializedName("course_Id") val courseId: Int,
    val course: Course
)