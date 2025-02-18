package com.course.CourseMedia.service.impl

import com.course.CourseMedia.auth.model.User
import com.course.CourseMedia.dto.CreateCourseRequest
import com.course.CourseMedia.model.Course
import com.course.CourseMedia.repository.CourseRepository
import com.course.CourseMedia.service.CourseService
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

@Service
class CourseServiceImpl(
    private val courseRepository: CourseRepository
) : CourseService {

    @Transactional
    override fun createCourse(request: CreateCourseRequest, creator: User): Course {
        logger.info { "Creating course: ${request.title} by ${creator.email}" }

        val course = Course(
            title = request.title,
            description = request.description,
            price = request.price,
            creator = creator
        )

        return courseRepository.save(course).also {
            logger.info { "Course created successfully: ${it.id}" }
        }
    }


}
