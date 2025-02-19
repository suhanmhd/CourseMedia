package com.course.CourseMedia.dto

import java.math.BigDecimal

data class StatsResponseDTO(
    val totalPurchases: Int,
    val totalRevenue: BigDecimal,
    val creators: List<CreatorStatsDTO>,
    val customers: List<CustomerStatsDTO>
)
