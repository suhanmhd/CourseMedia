package  com.course.CourseMedia.auth.service

import com.course.CourseMedia.Mapper.UserMapper
import com.course.CourseMedia.advice.exceptions.AuthenticationException
import com.course.CourseMedia.advice.exceptions.BadCredentialsException
import com.course.CourseMedia.advice.exceptions.EmailAlreadyExistsException
import com.course.CourseMedia.advice.exceptions.ResourceNotFoundException
import com.course.CourseMedia.auth.dto.AuthRequest
import com.course.CourseMedia.auth.dto.AuthResponse
import com.course.CourseMedia.auth.dto.RefreshTokenRequest
import com.course.CourseMedia.auth.dto.RegisterRequest
import com.course.CourseMedia.auth.enum.Role
import com.course.CourseMedia.auth.model.User
import com.course.CourseMedia.auth.repository.UserRepository
import com.course.CourseMedia.auth.security.JwtUtil
import com.course.CourseMedia.dto.UserResponseDTO
import mu.KotlinLogging
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil,
    private val authenticationManager: AuthenticationManager
) {
    private val logger = KotlinLogging.logger {}




    fun register(request: RegisterRequest): UserResponseDTO {
        logger.info("Attempting to register user with email: ${request.email}")

        if (userRepository.findByEmail(request.email).isPresent) {
            logger.warn("Registration failed: Email already taken - ${request.email}")
            throw EmailAlreadyExistsException("Email is already taken!")
        }

        val role = try {
            Role.valueOf(request.role.toString()) // Assuming request.role is a valid Role enum
        } catch (e: IllegalArgumentException) {
            logger.error("Invalid role provided: ${request.role}")
            throw ResourceNotFoundException("Invalid role: Must be ADMIN, CREATOR, or CUSTOMER")
        }

        val user = User(
            name = request.name,
            email = request.email,
            passwordHash = passwordEncoder.encode(request.password),
            role = role
        )

        val savedUser = userRepository.save(user)
        logger.info("User registered successfully: ${savedUser.email}")

        return UserMapper.toDto(user)
    }

    fun authenticate(request: AuthRequest): AuthResponse {
        logger.info("Attempting Authenticate: ${request.email}")


            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    request.email,
                   request.password
                )
            )

     val user = userRepository.findByEmail(request.email)
            .orElse(null)
            ?: throw BadCredentialsException("Invalid email or password")

        return AuthResponse(
            accessToken = jwtUtil.generateAccessToken(user),
            refreshToken = jwtUtil.generateRefreshToken(user)
        )
    }

    fun refreshToken(request: RefreshTokenRequest): AuthResponse {
        if (jwtUtil.isTokenExpired(request.refreshToken)) {
            throw IllegalArgumentException("Refresh token has expired")
        }

        val email = jwtUtil.extractUsername(request.refreshToken)
        val user = userRepository.findByEmail(email)
            .orElseThrow { IllegalArgumentException("User not found") }

        return AuthResponse(
            accessToken = jwtUtil.generateAccessToken(user),
            refreshToken = jwtUtil.generateRefreshToken(user)
        )
    }

}
