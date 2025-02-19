package com.course.CourseMedia.dto

import com.course.CourseMedia.model.PurchaseStatus
import java.math.BigDecimal
import java.time.LocalDateTime



data class PurchaseResponseDTO(
    val id: Long,
    val courseId: Long,
    val courseTitle: String,
    val amount: BigDecimal,
    val purchaseDate: LocalDateTime,
    val status: PurchaseStatus
)

