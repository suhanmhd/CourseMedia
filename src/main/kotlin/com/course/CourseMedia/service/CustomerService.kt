package com.course.CourseMedia.service

import com.course.CourseMedia.dto.CourseResponseDTO
import com.course.CourseMedia.dto.PurchaseResponseDTO


interface CustomerService {
    fun getAllCourses(search: String?): List<CourseResponseDTO>
    fun buyCourse(customerEmail: String, courseId: Long): PurchaseResponseDTO
    fun getUserPurchases(customerEmail: String): List<PurchaseResponseDTO>
}