package com.course.CourseMedia.Mapper

import com.course.CourseMedia.auth.model.User
import com.course.CourseMedia.dto.UserResponseDTO

object UserMapper {
    fun toDto(user: User): UserResponseDTO {
        return UserResponseDTO(
            id = user.id!!,
            email = user.email,
            name = user.name,
            role = user.role
        )
    }
}