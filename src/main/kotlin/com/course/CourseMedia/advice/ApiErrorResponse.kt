package com.course.CourseMedia.advice

import org.springframework.http.HttpStatus
import java.time.Instant

data class ApiErrorResponse(
    val httpStatus: HttpStatus,
    val message: String?,
    val path: String,
    val api: String,
    val timestamp: Instant = Instant.now()
)