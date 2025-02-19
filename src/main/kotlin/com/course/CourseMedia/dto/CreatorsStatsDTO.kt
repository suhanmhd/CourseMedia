package com.course.CourseMedia.dto

import java.math.BigDecimal

data class CreatorsStatsDTO(
    val creatorEmail: String,
    val totalCoursesSold: Int,
    val totalRevenueEarned: BigDecimal,
    val coursePurchases: List<CoursePurchaseDTO> // New field
)

data class CoursePurchaseDTO(
    val courseTitle: String,
    val coursePrice: BigDecimal,
    val customerEmail: String
)
