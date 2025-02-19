package com.course.CourseMedia.dto

import com.course.CourseMedia.auth.enum.Role


data class UserResponseDTO(
    val id: Long,
    val email: String,
    val name: String,
    val role: Role
)

