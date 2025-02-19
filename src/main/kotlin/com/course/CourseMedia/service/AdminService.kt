package com.course.CourseMedia.service

import com.course.CourseMedia.auth.enum.Role
import com.course.CourseMedia.dto.StatsResponseDTO
import com.course.CourseMedia.dto.UserResponseDTO
import java.time.LocalDate
import java.time.LocalDateTime

interface AdminService {
    fun getAllUsers(): List<UserResponseDTO>
    fun getDetailedStats(startDate: LocalDateTime?, endDate: LocalDateTime?): StatsResponseDTO
    fun getUsersByRole(role: Role): List<UserResponseDTO>
}