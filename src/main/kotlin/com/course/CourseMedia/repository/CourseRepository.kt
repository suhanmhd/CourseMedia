package com.course.CourseMedia.repository

import com.course.CourseMedia.auth.model.User
import com.course.CourseMedia.model.Course
import org.springframework.data.jpa.repository.JpaRepository

interface CourseRepository : JpaRepository<Course, Long> {
    fun findByCreator(creator: User): List<Course>
}
