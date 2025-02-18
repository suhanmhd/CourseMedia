package com.course.CourseMedia.service

import com.course.CourseMedia.auth.model.User
import com.course.CourseMedia.dto.CreateCourseRequest
import com.course.CourseMedia.model.Course


interface CourseService {
    fun createCourse(request: CreateCourseRequest, creator: User): Course
}
