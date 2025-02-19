package com.course.CourseMedia.repository

import com.course.CourseMedia.auth.model.User
import com.course.CourseMedia.model.Course
import com.course.CourseMedia.model.Purchase
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

interface PurchaseRepository : JpaRepository<Purchase, Long> {
//    fun findByUser(user: User): List<Purchase>
    fun existsByCustomerAndCourse(customer: User, course: Course): Boolean
    fun findByCustomer(customer: User): List<Purchase>
    @Query("""
        SELECT p FROM Purchase p 
        WHERE (:startDate IS NULL OR p.purchaseDate >= :startDate) 
        AND (:endDate IS NULL OR p.purchaseDate <= :endDate)
    """)
    fun findPurchasesBetweenDates(
        @Param("startDate") startDate: LocalDateTime?,
        @Param("endDate") endDate: LocalDateTime?
    ): List<Purchase>
}
