package com.course.CourseMedia.model

import com.course.CourseMedia.auth.model.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Purchase(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne
    val user: User, // The customer who bought the course
    @ManyToOne
    val course: Course, // The course that was bought
    val purchaseDate: LocalDateTime = LocalDateTime.now()
)

