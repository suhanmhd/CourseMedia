package com.course.CourseMedia.controller

import com.course.CourseMedia.auth.enum.Role
import com.course.CourseMedia.auth.model.User
import com.course.CourseMedia.dto.CreateCourseRequest
import com.course.CourseMedia.model.Course
import com.course.CourseMedia.service.CourseService
import jakarta.validation.Valid
import mu.KotlinLogging

import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/course")
class CourseController(private val courseService: CourseService) {

    @PostMapping
    fun createCourse(
        @Valid @RequestBody request: CreateCourseRequest,
        @AuthenticationPrincipal user: User
    ): ResponseEntity<Course> {
        logger.info { "Received request to create course by user: ${user.email}" }

        if (user.role != Role.CREATOR) {
            logger.warn { "Unauthorized access attempt by user: ${user.email}" }
            return ResponseEntity.status(403).build()
        }

        val createdCourse = courseService.createCourse(request, user)
        return ResponseEntity.ok(createdCourse)
    }
}
