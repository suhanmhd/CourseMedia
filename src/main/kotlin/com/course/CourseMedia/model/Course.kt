package com.course.CourseMedia.model

import com.course.CourseMedia.auth.model.User
import jakarta.persistence.*

import jakarta.persistence.*
import java.math.BigDecimal
//
//@Entity
//@Table(name = "courses")
//data class Course(
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id: Long = 0,
//
//    @Column(nullable = false)
//    var title: String,
//
//    @Column(nullable = false, columnDefinition = "TEXT")
//    var description: String,
//
//    @Column(nullable = false)
//    var price: BigDecimal,
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "creator_id", nullable = false)
//    val creator: User
//)



@Entity
@Table(name = "courses")
data class Course(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    var description: String,

    @Column(nullable = false, precision = 10, scale = 2)
    var price: BigDecimal,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    val creator: User
)
