package com.course.CourseMedia.auth.controller

import com.course.CourseMedia.auth.dto.AuthRequest
import com.course.CourseMedia.auth.dto.AuthResponse
import com.course.CourseMedia.auth.dto.RegisterRequest
import com.course.CourseMedia.auth.service.AuthService

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<AuthResponse> {
        println("helllooo")
        return ResponseEntity.ok(authService.register(request))
    }

    @PostMapping("/login")
    fun authenticate(@RequestBody request: AuthRequest): ResponseEntity<AuthResponse> {
        val response = authService.authenticate(request)
        return ResponseEntity.ok(response)
    }
}
