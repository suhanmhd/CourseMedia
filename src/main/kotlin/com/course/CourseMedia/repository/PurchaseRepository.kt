package com.course.CourseMedia.repository

import com.course.CourseMedia.auth.model.User
import com.course.CourseMedia.model.Course
import com.course.CourseMedia.model.Purchase
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate
import java.util.*

interface PurchaseRepository : JpaRepository<Purchase, Long> {
//    fun findByUser(user: User): List<Purchase>
    fun existsByCustomerAndCourse(customer: User, course: Course): Boolean
    fun findByCustomer(customer: User): List<Purchase>
    @Query("SELECT p FROM Purchase p WHERE (:startDate IS NULL OR p.purchaseDate >= :startDate) AND (:endDate IS NULL OR p.purchaseDate <= :endDate)")
    fun findPurchasesBetweenDates(startDate: LocalDate?, endDate: LocalDate?): List<Purchase>

}
