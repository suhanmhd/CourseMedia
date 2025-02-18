package com.course.CourseMedia.dto

import com.course.CourseMedia.model.Course
import java.math.BigDecimal


data class CourseResponseDTO(
    val id: Long?,
    val title: String,
    val description: String,
    val price: BigDecimal,
    val creatorEmail: String
) {
    companion object {
        fun fromEntity(course: Course): CourseResponseDTO {
            return CourseResponseDTO(
                id = course.id,
                title = course.title,
                description = course.description,
                price = course.price,
                creatorEmail = course.creator.email
            )
        }
    }
}

