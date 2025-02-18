package com.course.CourseMedia.controller

import com.course.CourseMedia.dto.CourseRequestDTO
import com.course.CourseMedia.dto.CourseResponseDTO
import com.course.CourseMedia.service.CourseService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}


@RestController
@RequestMapping("/api/courses")
class CourseController(
    private val courseService: CourseService
) {

    @PostMapping()
    fun createCourse(
        @RequestBody @Validated request: CourseRequestDTO,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<CourseResponseDTO> {
        val creatorEmail = userDetails.username
        logger.info { "Received request to create a course by user: $creatorEmail" }

        val course = courseService.createCourse(request, creatorEmail)
        return ResponseEntity.ok(course)
    }



    @GetMapping("/creator")
    fun getCreatorCourses(
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<List<CourseResponseDTO>> {
        val creatorEmail = userDetails.username
        logger.info { "Fetching courses for creator: $creatorEmail" }

        val courses = courseService.getCoursesByCreator(creatorEmail)
        return ResponseEntity.ok(courses)
    }

    @PutMapping("/{id}")
    fun updateCourse(
        @PathVariable id: Long,
        @RequestBody @Validated request: CourseRequestDTO,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<CourseResponseDTO> {
        val creatorEmail = userDetails.username
        logger.info { "Received request to update course $id by user: $creatorEmail" }

        val updatedCourse = courseService.updateCourse(id, request, creatorEmail)
        return ResponseEntity.ok(updatedCourse)
    }

    @DeleteMapping("/{id}")
    fun deleteCourse(
        @PathVariable id: Long,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<String> {
        val creatorEmail = userDetails.username
        logger.info { "Received request to delete course $id by user: $creatorEmail" }

        courseService.deleteCourse(id, creatorEmail)
        return ResponseEntity.ok("Course deleted successfully")
    }
}