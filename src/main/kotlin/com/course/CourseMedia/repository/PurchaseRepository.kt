package com.course.CourseMedia.repository

import com.course.CourseMedia.auth.model.User
import com.course.CourseMedia.model.Course
import com.course.CourseMedia.model.Purchase
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PurchaseRepository : JpaRepository<Purchase, Long> {
    fun findByUser(user: User): List<Purchase>
    fun existsByCustomerAndCourse(customer: Optional<User>, course: Course): Boolean
    fun findByCustomer(customer: User): List<Purchase>
}
