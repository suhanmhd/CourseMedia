package com.course.CourseMedia.auth.dto


import com.course.CourseMedia.auth.enum.Role
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size


data class RegisterRequest(
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email format")
    val email: String,

    @field:NotBlank(message = "Name cannot be empty")
    val name: String,


    @field:NotBlank(message = "Password is required")
    @field:Size(min = 6, message = "Password must be at least 6 characters")
    val password: String,


    @field:NotNull(message = "Role is required")
    val role: Role
)

//data class AuthResponse(
//    val accessToken: String,
//    val refreshToken: String
//)
