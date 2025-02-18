package com.course.CourseMedia.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import java.math.BigDecimal

data class CourseRequestDTO(
    @field:NotBlank(message = "Title cannot be empty")
    val title: String,

    @field:NotBlank(message = "Description cannot be empty")
    val description: String,

    @field:Min(value = 0, message = "Price must be greater than or equal to 0")
    val price: BigDecimal
)
