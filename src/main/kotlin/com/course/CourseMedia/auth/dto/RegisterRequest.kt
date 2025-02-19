package com.course.CourseMedia.auth.dto


import com.course.CourseMedia.auth.enum.Role
import jakarta.validation.constraints.*


data class RegisterRequest(
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email format")
    val email: String,

    @field:NotBlank(message = "Name cannot be empty")
    val name: String,


    @field:NotBlank(message = "Password is required")
    @field:Size(min = 6, message = "Password must be at least 6 characters")
    val password: String,

    @field:NotBlank(message = "Role is required")
    @field:Pattern(
        regexp = "ADMIN|CREATOR|CUSTOMER",
        message = "Invalid role: Must be ADMIN, CREATOR, or CUSTOMER"
    )
    val role: String

)

