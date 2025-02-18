package com.course.CourseMedia.Mapper

import com.course.CourseMedia.dto.CourseResponseDTO
import com.course.CourseMedia.model.Course


object CourseMapper {
    fun toResponseDTO(course: Course): CourseResponseDTO {
        return CourseResponseDTO(
            id = course.id!!,
            title = course.title,
            description = course.description,
            price = course.price,
            creatorEmail = course.creator.email
        )
    }
}
