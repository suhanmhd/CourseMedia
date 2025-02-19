package com.course.CourseMedia.controller

import com.course.CourseMedia.dto.CourseResponseDTO
import com.course.CourseMedia.dto.PurchaseResponseDTO
import com.course.CourseMedia.service.CustomerService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/customer/course")
class CustomerController(
    private val customerService: CustomerService
) {
    private val logger = KotlinLogging.logger {}

    @GetMapping()
    fun getAllCourses(
        @RequestParam(required = false) search: String?
    ): ResponseEntity<List<CourseResponseDTO>> {
        logger.info { "Fetching courses with search query: $search" }
        val courses = customerService.getAllCourses(search)
        logger.info { "Successfully fetched ${courses.size} courses" }
        return ResponseEntity.ok(courses)
    }

    @PostMapping("/purchase/{courseId}")
    fun buyCourse(
        @PathVariable courseId: Long,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<PurchaseResponseDTO> {
        val customerEmail = userDetails.username
        logger.info { "Purchase request received for user: $customerEmail" }

        val purchase = customerService.buyCourse(customerEmail, courseId)
        return ResponseEntity.status(HttpStatus.CREATED).body(purchase)
    }



    @GetMapping("/my-courses")
    fun getUserPurchases(
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<List<PurchaseResponseDTO>> {
        val customerEmail = userDetails.username
        val purchases = customerService.getUserPurchases(customerEmail.toString())
        return ResponseEntity.ok(purchases)
    }

}