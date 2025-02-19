package com.course.CourseMedia.service.impl

import com.course.CourseMedia.Mapper.CourseMapper
import com.course.CourseMedia.advice.exceptions.CourseNotFoundException
import com.course.CourseMedia.advice.exceptions.UnauthorizedAccessException
import com.course.CourseMedia.auth.enum.Role
import com.course.CourseMedia.auth.repository.UserRepository
import com.course.CourseMedia.dto.*
import com.course.CourseMedia.model.Course
import com.course.CourseMedia.repository.CourseRepository
import com.course.CourseMedia.repository.PurchaseRepository
import com.course.CourseMedia.service.CourseService
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Private

private val logger = KotlinLogging.logger {}

@Service
class CourseServiceImpl(
    private val courseRepository: CourseRepository,
    private val userRepository: UserRepository,
    private val purchaseRepository: PurchaseRepository
) : CourseService {

    @Transactional
    override fun createCourse(request: CourseRequestDTO, creatorEmail: String): CourseResponseDTO {
        logger.info { "Creating course: ${request.title} by $creatorEmail" }

        val creator = userRepository.findByEmail(creatorEmail)
            .orElseThrow({UnauthorizedAccessException("Creator not found!")})


        if (creator.role != Role.CREATOR) {
            throw UnauthorizedAccessException("Only Creators can add courses!")
        }

        val course = Course(
            title = request.title,
            description = request.description,
            price = request.price,
            creator = creator
        )

        val savedCourse = courseRepository.save(course)
        logger.info { "Course created successfully: ${savedCourse.id}" }

        return CourseResponseDTO.fromEntity(savedCourse)
    }

    override fun getCoursesByCreator(creatorEmail: String): List<CourseResponseDTO> {
        logger.info { "Fetching courses for creator: $creatorEmail" }

        val creator = userRepository.findByEmail(creatorEmail)
            .orElseThrow( {UnauthorizedAccessException("Creator not found!")})

        val courses = courseRepository.findByCreator(creator)

        return courses.map { CourseResponseDTO.fromEntity(it) }
    }
    @Transactional
    override fun updateCourse(id: Long, request: CourseRequestDTO, creatorEmail: String): CourseResponseDTO {
        val course = courseRepository.findById(id)
            .orElseThrow { CourseNotFoundException("Course with ID $id not found") }

        if (course.creator.email != creatorEmail) {
            throw UnauthorizedAccessException("You are not authorized to update this course")
        }

        course.title = request.title
        course.description = request.description
        course.price = request.price

        val updatedCourse = courseRepository.save(course)
        logger.info { "Course with ID $id updated successfully by $creatorEmail" }

        return CourseMapper.toResponseDTO(updatedCourse)
    }

    @Transactional
    override fun deleteCourse(id: Long, creatorEmail: String) {
        val course = courseRepository.findById(id)
            .orElseThrow { CourseNotFoundException("Course with ID $id not found") }

        if (course.creator.email != creatorEmail) {
            throw UnauthorizedAccessException("You are not authorized to delete this course")
        }

        courseRepository.delete(course)
        logger.info { "Course with ID $id deleted successfully by $creatorEmail" }
    }


    override fun getCreatorStats(creatorEmail: String, startDate: LocalDateTime?, endDate: LocalDateTime?): CreatorsStatsDTO {
        logger.info { "Calculating statistics for creator: $creatorEmail" }

        val purchases = purchaseRepository.findByCreatorEmailAndDateRange(creatorEmail, startDate, endDate)

        val coursePurchases = purchases.map { purchase ->
            CoursePurchaseDTO(
                courseTitle = purchase.course.title,
                coursePrice = purchase.course.price,
                customerEmail = purchase.customer.email
            )
        }

        return CreatorsStatsDTO(
            creatorEmail = creatorEmail,
            totalCoursesSold = purchases.size,
            totalRevenueEarned = purchases.sumOf { it.course.price },
            coursePurchases = coursePurchases
        )
    }







}
