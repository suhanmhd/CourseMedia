package com.course.CourseMedia.service.impl

import com.course.CourseMedia.auth.enum.Role
import com.course.CourseMedia.auth.repository.UserRepository
import com.course.CourseMedia.dto.CreatorStatsDTO
import com.course.CourseMedia.dto.CustomerStatsDTO
import com.course.CourseMedia.dto.StatsResponseDTO
import com.course.CourseMedia.dto.UserResponseDTO
import com.course.CourseMedia.repository.PurchaseRepository
import com.course.CourseMedia.service.AdminService
import mu.KotlinLogging
import org.apache.coyote.BadRequestException
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class AdminServiceImpl (
    private val purchaseRepository: PurchaseRepository,
    private  val userRepository: UserRepository

):AdminService{
    private val logger = KotlinLogging.logger {}


    override fun getAllUsers(): List<UserResponseDTO> {
        logger.info { "Fetching all users from the database" }

        val users = userRepository.findAll()

        if (users.isEmpty()) {
            logger.warn { "No users found in the system" }
        }

        val userDtos = users.map { user ->
            UserResponseDTO(
                id = user.id,
                email = user.email,
                name = user.name,
                role = user.role
            )
        }
        logger.info { "Successfully mapped ${users.size} users to DTOs" }
        return userDtos
    }
    override fun getUsersByRole(role: Role): List<UserResponseDTO> {
        logger.info { "Fetching users with role: $role" }

        val users = userRepository.findByRole(role)

        logger.info { "Fetched ${users.size} users with role: $role" }

        val userDtos = users.map { user ->
            UserResponseDTO(
                id = user.id,
                email = user.email,
                name = user.name,
                role = user.role
            )
        }
        logger.info { "Successfully mapped ${users.size} users to DTOs" }
        return userDtos
    }





    override fun getDetailedStats(startDate: LocalDateTime?, endDate: LocalDateTime?): StatsResponseDTO {
        try {
            logger.info { "Fetching detailed statistics for period: $startDate to $endDate" }

            if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
                throw BadRequestException("Start date cannot be after end date.")
            }

            var purchases = purchaseRepository.findPurchasesBetweenDates(startDate, endDate)

             purchases = purchases.filter { purchase ->
                (startDate == null || !purchase.purchaseDate.isBefore(startDate)) &&
                        (endDate == null || !purchase.purchaseDate.isAfter(endDate))
            }

            // Calculate overall stats
            val totalRevenue = purchases.sumOf { it.amount }
            val totalPurchases = purchases.size

            // Group by Creator
            val creatorStats = purchases.groupBy { it.course.creator.email }.map { (creatorEmail, purchases) ->
                CreatorStatsDTO(
                    creatorEmail = creatorEmail,
                    totalCoursesSold = purchases.size,
                    totalRevenueEarned = purchases.sumOf { it.amount }
                )
            }

            // Group by Customer
            val customerStats = purchases.groupBy { it.customer.email }.map { (customerEmail, purchases) ->
                CustomerStatsDTO(
                    customerEmail = customerEmail,
                    totalCoursesBought = purchases.size,
                    totalAmountSpent = purchases.sumOf { it.amount }
                )
            }

            val stats = StatsResponseDTO(
                totalPurchases = totalPurchases,
                totalRevenue = totalRevenue,
                creators = creatorStats,
                customers = customerStats
            )

            logger.info { "Detailed statistics calculated: $stats" }
            return stats

        } catch (e: Exception) {
            logger.error(e) { "Error fetching purchase statistics" }
            throw RuntimeException("Failed to fetch purchase statistics", e)
        }
    }



}