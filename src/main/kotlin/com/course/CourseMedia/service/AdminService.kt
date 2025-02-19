package com.course.CourseMedia.service

import com.course.CourseMedia.dto.StatsResponseDTO
import com.course.CourseMedia.dto.UserResponseDTO
import java.time.LocalDate

interface AdminService {
    fun getAllUsers(): List<UserResponseDTO>
    fun getDetailedStats(startDate: LocalDate?, endDate: LocalDate?): StatsResponseDTO
}