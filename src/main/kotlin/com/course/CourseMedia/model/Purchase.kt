package com.course.CourseMedia.model

import com.course.CourseMedia.auth.model.User
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
//
//@Entity
//@Table(name = "purchases")
//data class Purchase(
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id: Long = 0,
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    val customer: User,
//
//    @ManyToOne
//    @JoinColumn(name = "course_id", nullable = false)
//    val course: Course,
//
//    @Column(nullable = false)
//    val purchaseDate: LocalDateTime = LocalDateTime.now(),
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    val status: PurchaseStatus = PurchaseStatus.COMPLETED
//)
//
//
//enum class PurchaseStatus {
//    COMPLETED, REFUNDED, CANCELED
//}




@Entity
@Table(name = "purchases")
data class Purchase(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val customer: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    val course: Course,

    @Column(nullable = false)
    val amount: BigDecimal,

    @Column(nullable = false)
    val purchaseDate: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: PurchaseStatus = PurchaseStatus.COMPLETED
)

enum class PurchaseStatus {
    COMPLETED, REFUNDED, CANCELED
}