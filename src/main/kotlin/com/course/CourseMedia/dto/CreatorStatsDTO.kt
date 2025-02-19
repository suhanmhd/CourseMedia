package com.course.CourseMedia.dto

import java.math.BigDecimal

data class CreatorStatsDTO(
    val creatorEmail: String,
    val totalCoursesSold: Int,
    val totalRevenueEarned: BigDecimal
)
