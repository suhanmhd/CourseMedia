package com.course.CourseMedia.service.impl

import com.course.CourseMedia.advice.exceptions.ConflictException
import com.course.CourseMedia.advice.exceptions.ResourceNotFoundException
import com.course.CourseMedia.advice.exceptions.UserNotFoundException
import com.course.CourseMedia.auth.repository.UserRepository
import com.course.CourseMedia.dto.CourseResponseDTO
import com.course.CourseMedia.dto.PurchaseResponseDTO
import com.course.CourseMedia.model.Purchase
import com.course.CourseMedia.repository.CourseRepository
import com.course.CourseMedia.repository.PurchaseRepository
import com.course.CourseMedia.service.CustomerService
import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
 class CustomerServiceImpl(
    private val courseRepository: CourseRepository,
    private val purchaseRepository: PurchaseRepository,
    private  val userRepository: UserRepository
) : CustomerService {

    private val logger = KotlinLogging.logger {}

    override fun getAllCourses(search: String?): List<CourseResponseDTO> {
        return try {
            logger.info { "Fetching courses with search query: $search" }

            val courses = if (!search.isNullOrBlank()) {
                courseRepository.findByTitleContainingIgnoreCase(search)
            } else {
                courseRepository.findAll()
            }

            logger.info { "Successfully fetched ${courses.size} courses" }

            courses.map { course ->
                CourseResponseDTO(
                    id = course.id,
                    title = course.title,
                    description = course.description,
                    price = course.price,
                    creatorEmail = course.creator.email
                )
            }
        } catch (e: Exception) {
            logger.error(e) { "Error fetching courses" }
            throw RuntimeException("Failed to fetch courses", e)
        }
    }


    @Transactional


    override fun buyCourse(customerEmail: String, courseId: Long): PurchaseResponseDTO {
        val customer = userRepository.findByEmail(customerEmail)
            .orElseThrow { UserNotFoundException("User with email $customerEmail not found") }

        val course = courseRepository.findById(courseId)
            .orElseThrow { ResourceNotFoundException("Course not found with id $courseId") }

        if (purchaseRepository.existsByCustomerAndCourse(customer, course)) {
            throw ConflictException("You have already purchased this course.")
        }

        val purchase = purchaseRepository.save(Purchase(customer = customer, course = course))
        logger.info { "User ${customer.email} purchased course ${course.title}" }

        return purchase.toDTO()
    }

    override fun getUserPurchases(customerEmail: String): List<PurchaseResponseDTO> {
        logger.info { "Fetching purchases for customer: $customerEmail" }

        val customer = userRepository.findByEmail(customerEmail)
            .orElseThrow {  UserNotFoundException("User with email $customerEmail not found")}



        val purchases = purchaseRepository.findByCustomer(customer)

        if (purchases.isEmpty()) {
            logger.info { "No purchases found for customer: $customerEmail" }
        }

        return purchases.map { it.toDTO() }
    }

    private fun Purchase.toDTO(): PurchaseResponseDTO {
        return PurchaseResponseDTO(
            id = id,
            courseId = course.id,
            courseTitle = course.title,
            purchaseDate = purchaseDate,
            status = status
        )
    }



}