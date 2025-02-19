package com.course.CourseMedia.dto

import java.time.LocalDate

data class DateRangeRequest(
    val startDate: LocalDate?,
    val endDate: LocalDate?
)
