package com.course.CourseMedia.controller

import com.course.CourseMedia.auth.enum.Role
import com.course.CourseMedia.dto.CreatorStatsDTO
import com.course.CourseMedia.dto.CreatorsStatsDTO
import com.course.CourseMedia.dto.StatsResponseDTO
import com.course.CourseMedia.dto.UserResponseDTO
import com.course.CourseMedia.service.AdminService
import mu.KotlinLogging

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate


@RestController
@RequestMapping("/api/v1/admin")
class AdminController(
    private  val adminService: AdminService
) {

    private val logger = KotlinLogging.logger {}
    @GetMapping("/users")
    fun getAllUsers(): ResponseEntity<List<UserResponseDTO>> {
        logger.info { "Fetching all users (Creators and Customers)" }

        val users = adminService.getAllUsers()

        logger.info { "Successfully fetched ${users.size} users" }
        return ResponseEntity.ok(users)
    }


    @GetMapping("/users/creators")
    fun getAllCreators(): ResponseEntity<List<UserResponseDTO>> {
        logger.info { "Fetching all creators" }

        val creators = adminService.getUsersByRole(Role.CREATOR)

        logger.info { "Successfully fetched ${creators.size} creators" }
        return ResponseEntity.ok(creators)
    }

    @GetMapping("/users/customers")
    fun getAllCustomers(): ResponseEntity<List<UserResponseDTO>> {
        logger.info { "Fetching all customers" }

        val customers = adminService.getUsersByRole(Role.CUSTOMER)

        logger.info { "Successfully fetched ${customers.size} customers" }
        return ResponseEntity.ok(customers)
    }


    @GetMapping("/statics")
    fun getPurchaseStats(
        @RequestParam(required = false) startDate: LocalDate?,
        @RequestParam(required = false)  endDate: LocalDate?
    ): ResponseEntity<StatsResponseDTO> {
        logger.info { "Fetching full purchase stats from $startDate to $endDate" }
        val startDateTime = startDate?.atStartOfDay()
        val endDateTime = endDate?.atTime(23, 59, 59)
        val stats = adminService.getDetailedStats(startDateTime, endDateTime)

        logger.info { "Successfully fetched stats: $stats" }
        return ResponseEntity.ok(stats)
    }

}