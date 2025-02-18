package com.course.CourseMedia.repository

import com.course.CourseMedia.auth.model.User
import com.course.CourseMedia.model.Purchase
import org.springframework.data.jpa.repository.JpaRepository

interface PurchaseRepository : JpaRepository<Purchase, Long> {
    fun findByUser(user: User): List<Purchase>
}
