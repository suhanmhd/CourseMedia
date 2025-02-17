package com.course.CourseMedia.auth.dto


import jakarta.validation.constraints.NotBlank

data class RefreshTokenRequest(
    @field:NotBlank(message = "Refresh token cannot be empty")
    val refreshToken: String
)
