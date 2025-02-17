package com.course.CourseMedia.auth.repository

import com.course.CourseMedia.auth.model.RefreshToken
import com.course.CourseMedia.auth.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {
    fun findByToken(token: String): Optional<RefreshToken>
    fun deleteByUser(user: User)
}
