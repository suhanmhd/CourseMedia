//package com.course.CourseMedia.advice.handler
//
//
//import com.course.CourseMedia.advice.exceptions.*
//import jakarta.servlet.http.HttpServletRequest
//import org.slf4j.LoggerFactory
//import org.springframework.http.HttpStatus
//import org.springframework.http.ProblemDetail
//import org.springframework.http.ResponseEntity
//import org.springframework.security.access.AccessDeniedException
//import org.springframework.web.bind.MethodArgumentNotValidException
//import org.springframework.web.bind.annotation.ExceptionHandler
//import org.springframework.web.bind.annotation.RestControllerAdvice
//import java.time.Instant
//
//@RestControllerAdvice
//class GlobalExceptionHandler {
//
//    private val log = LoggerFactory.getLogger(this::class.java)
//
//    // Handle validation errors (e.g., @Valid failures)
//    @ExceptionHandler(MethodArgumentNotValidException::class)
//    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ProblemDetail {
//        val errors = ex.bindingResult.fieldErrors.associate { it.field to (it.defaultMessage ?: "No message available") }
//        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed").apply {
//            setProperty("errors", errors)
//            setProperty("timestamp", Instant.now())
//        }
//    }
//
//    // Handle access denied exceptions (e.g., unauthorized access)
//    @ExceptionHandler(AccessDeniedException::class)
//    fun handleAccessDeniedException(ex: AccessDeniedException): ResponseEntity<String> {
//        log.warn("Access denied: ${ex.message}")
//        return ResponseEntity("You do not have permission to access this resource. Admin rights are required.", HttpStatus.FORBIDDEN)
//    }
//
//    // Handle resource not found exceptions
////    @ExceptionHandler(ResourceNotFoundException::class)
////    fun handleResourceNotFoundException(ex: ResourceNotFoundException, request: HttpServletRequest): ProblemDetail {
////        log.warn("Resource not found: ${ex.message}")
////        return buildProblemDetail(HttpStatus.NOT_FOUND, ex.message, request)
////    }
//
//    // Handle user not found exceptions
//    @ExceptionHandler(UserNotFoundException::class)
//    fun handleUserNotFoundException(ex: UserNotFoundException, request: HttpServletRequest): ProblemDetail {
//        log.warn("User not found: ${ex.message}")
//        return buildProblemDetail(HttpStatus.NOT_FOUND, ex.message, request)
//    }
//
//    // Handle resource creation failures
//    @ExceptionHandler(ResourceCreationException::class)
//    fun handleResourceCreationException(ex: ResourceCreationException, request: HttpServletRequest): ProblemDetail {
//        log.error("Resource creation failed: ${ex.message}", ex)
//        return buildProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.message, request)
//    }
//
//    @ExceptionHandler(AuthenticationException::class)
//    fun handleAuthenticationException(ex:  AuthenticationException, request: HttpServletRequest): ProblemDetail {
//        log.warn("Bad credentials: ${ex.message}")
//        return buildProblemDetail(HttpStatus.UNAUTHORIZED, "Invalid credentials", request)
//    }
//    // Handle bad credentials (e.g., login failures)
//    @ExceptionHandler(BadCredentialsException::class)
//    fun handleBadCredentialException(ex: BadCredentialsException, request: HttpServletRequest): ProblemDetail {
//        log.warn("Bad credentials: ${ex.message}")
//        return buildProblemDetail(HttpStatus.UNAUTHORIZED, "Invalid credentials", request)
//    }
//
//    // Handle unauthorized access
//    @ExceptionHandler(UnauthorizedAccessException::class)
//    fun handleUnauthorizedAccessException(ex: UnauthorizedAccessException, request: HttpServletRequest): ProblemDetail {
//        log.warn("Unauthorized access: ${ex.message}")
//        return buildProblemDetail(HttpStatus.UNAUTHORIZED, ex.message, request)
//    }
//    @ExceptionHandler(ResourceNotFoundException::class)
//    fun handleResourceNotFoundException(ex: ResourceNotFoundException, request: HttpServletRequest): ProblemDetail {
//        log.warn("Resource Not found: ${ex.message}")
//        return buildProblemDetail(HttpStatus.CONFLICT, ex.message, request)
//    }
//    // Handle email already exists conflicts
//    @ExceptionHandler(EmailAlreadyExistsException::class)
//    fun handleEmailAlreadyExistsException(ex: EmailAlreadyExistsException, request: HttpServletRequest): ProblemDetail {
//        log.warn("Email already exists: ${ex.message}")
//        return buildProblemDetail(HttpStatus.CONFLICT, ex.message, request)
//    }
//
//    // Handle database-related exceptions
//    @ExceptionHandler(DatabaseException::class)
//    fun handleDatabaseException(ex: DatabaseException, request: HttpServletRequest): ProblemDetail {
//        log.error("Database error: ${ex.message}", ex)
//        return buildProblemDetail(HttpStatus.SERVICE_UNAVAILABLE, "Database operation failed", request)
//    }
//
//    @ExceptionHandler(ConflictException::class)
//    fun handleConflictException(ex: DatabaseException, request: HttpServletRequest): ProblemDetail {
//        log.error("Database error: ${ex.message}", ex)
//        return buildProblemDetail(HttpStatus.CONFLICT, ex.message, request)
//    }
//
//
//    // Global fallback for unhandled exceptions
//    @ExceptionHandler(Exception::class)
//    fun handleGenericException(ex: Exception, request: HttpServletRequest): ProblemDetail {
//        log.error("Unhandled exception occurred: ${ex.message}", ex)
//        return buildProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", request)
//    }
//
//    // Helper function to build ProblemDetail responses
//    private fun buildProblemDetail(
//        status: HttpStatus,
//        message: String?,
//        request: HttpServletRequest
//    ): ProblemDetail {
//        return ProblemDetail.forStatusAndDetail(status, message ?: "No message available").apply {
//            setProperty("path", request.requestURI)
//            setProperty("timestamp", Instant.now())
//        }
//    }
//}











package com.course.CourseMedia.advice.handler

import com.course.CourseMedia.advice.exceptions.*
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.Instant

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(this::class.java)

    // Handle validation errors (e.g., @Valid failures)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ProblemDetail> {
        val errors = ex.bindingResult.fieldErrors.associate { it.field to (it.defaultMessage ?: "No message available") }
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed").apply {
            setProperty("errors", errors)
            setProperty("timestamp", Instant.now())
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail)
    }

    // Handle access denied exceptions
    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(ex: AccessDeniedException, request: HttpServletRequest): ResponseEntity<ProblemDetail> {
        log.warn("Access denied: ${ex.message}")
        val problemDetail = buildProblemDetail(HttpStatus.FORBIDDEN, "Admin rights required.", request)
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problemDetail)
    }

    // Handle user not found exceptions
    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(ex: UserNotFoundException, request: HttpServletRequest): ResponseEntity<ProblemDetail> {
        log.warn("User not found: ${ex.message}")
        val problemDetail = buildProblemDetail(HttpStatus.NOT_FOUND, ex.message, request)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail)
    }

    // Handle resource creation failures
    @ExceptionHandler(ResourceCreationException::class)
    fun handleResourceCreationException(ex: ResourceCreationException, request: HttpServletRequest): ResponseEntity<ProblemDetail> {
        log.error("Resource creation failed: ${ex.message}", ex)
        val problemDetail = buildProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.message, request)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail)
    }

    // Handle authentication errors
    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(ex: AuthenticationException, request: HttpServletRequest): ResponseEntity<ProblemDetail> {
        log.warn("Authentication failed: ${ex.message}")
        val problemDetail = buildProblemDetail(HttpStatus.UNAUTHORIZED, "Authentication failed. Check your credentials.", request)
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail)
    }

    // Handle login failures (Bad Credentials)
    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentialException(ex: BadCredentialsException, request: HttpServletRequest): ResponseEntity<ProblemDetail> {
        log.warn("Invalid credentials for path: ${request.requestURI}")
        val problemDetail = buildProblemDetail(HttpStatus.UNAUTHORIZED, "Invalid email or password.", request)
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail)
    }

    // Handle unauthorized access
    @ExceptionHandler(UnauthorizedAccessException::class)
    fun handleUnauthorizedAccessException(ex: UnauthorizedAccessException, request: HttpServletRequest): ResponseEntity<ProblemDetail> {
        log.warn("Unauthorized access: ${ex.message}")
        val problemDetail = buildProblemDetail(HttpStatus.UNAUTHORIZED, ex.message, request)
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail)
    }

    // Handle resource not found conflicts
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(ex: ResourceNotFoundException, request: HttpServletRequest): ResponseEntity<ProblemDetail> {
        log.warn("Resource Not found: ${ex.message}")
        val problemDetail = buildProblemDetail(HttpStatus.NOT_FOUND, ex.message, request)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail)
    }

    // Handle email already exists conflicts
    @ExceptionHandler(EmailAlreadyExistsException::class)
    fun handleEmailAlreadyExistsException(ex: EmailAlreadyExistsException, request: HttpServletRequest): ResponseEntity<ProblemDetail> {
        log.warn("Email already exists: ${ex.message}")
        val problemDetail = buildProblemDetail(HttpStatus.CONFLICT, ex.message, request)
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail)
    }

    // Handle database-related exceptions
    @ExceptionHandler(DatabaseException::class)
    fun handleDatabaseException(ex: DatabaseException, request: HttpServletRequest): ResponseEntity<ProblemDetail> {
        log.error("Database error: ${ex.message}", ex)
        val problemDetail = buildProblemDetail(HttpStatus.SERVICE_UNAVAILABLE, "Database operation failed", request)
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(problemDetail)
    }

    // Handle general conflicts
    @ExceptionHandler(ConflictException::class)
    fun handleConflictException(ex: ConflictException, request: HttpServletRequest): ResponseEntity<ProblemDetail> {
        log.error("Conflict error: ${ex.message}", ex)
        val problemDetail = buildProblemDetail(HttpStatus.CONFLICT, ex.message, request)
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail)
    }

    // Global fallback for unhandled exceptions
    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception, request: HttpServletRequest): ResponseEntity<ProblemDetail> {
        log.error("Unhandled exception occurred: ${ex.message}", ex)
        val problemDetail = buildProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", request)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail)
    }

    // Helper function to build ProblemDetail responses
    private fun buildProblemDetail(
        status: HttpStatus,
        message: String?,
        request: HttpServletRequest
    ): ProblemDetail {
        return ProblemDetail.forStatusAndDetail(status, message ?: "No message available").apply {
            setProperty("path", request.requestURI)
            setProperty("timestamp", Instant.now())
        }
    }
}
