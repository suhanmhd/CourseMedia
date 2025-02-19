package com.course.CourseMedia.dto

import java.math.BigDecimal


data class CustomerStatsDTO(
    val customerEmail: String,
    val totalCoursesBought: Int,
    val totalAmountSpent: BigDecimal
)