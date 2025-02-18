package com.course.CourseMedia.service


import com.course.CourseMedia.dto.CourseRequestDTO
import com.course.CourseMedia.dto.CourseResponseDTO




interface CourseService {
    fun createCourse(request: CourseRequestDTO, creatorEmail: String): CourseResponseDTO
    fun getCoursesByCreator(creatorEmail: String): List<CourseResponseDTO>
    fun updateCourse(id: Long, request: CourseRequestDTO, creatorEmail: String): CourseResponseDTO
    fun deleteCourse(id: Long, creatorEmail: String)
}