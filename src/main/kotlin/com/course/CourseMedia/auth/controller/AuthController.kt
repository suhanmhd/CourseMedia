package com.course.CourseMedia.auth.controller

import com.course.CourseMedia.auth.dto.AuthRequest
import com.course.CourseMedia.auth.dto.AuthResponse
import com.course.CourseMedia.auth.dto.RefreshTokenRequest
import com.course.CourseMedia.auth.dto.RegisterRequest
import com.course.CourseMedia.auth.service.AuthService
import com.course.CourseMedia.dto.UserResponseDTO

import jakarta.validation.Valid
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/auth")

class AuthController(private val authService: AuthService) {

    private val logger = KotlinLogging.logger {}

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<UserResponseDTO> {
        logger.info("Received registration request for email: {}", request.email)
        val response = authService.register(request)
        logger.info("User registered successfully with email: {}", response.email)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/login")
    fun authenticate(@RequestBody request: AuthRequest): ResponseEntity<AuthResponse> {
        logger.info("Received authentication request for email: {}", request.email)
        val response = authService.authenticate(request)
        logger.info("User authenticated successfully for email: {}", request.email)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/refresh-token")
    fun refreshToken(@RequestBody request: RefreshTokenRequest): ResponseEntity<AuthResponse> {
        logger.info("Received refresh token request")
        val response = authService.refreshToken(request)
        logger.info("Token refreshed successfully")
        return ResponseEntity.ok(response)
    }
}