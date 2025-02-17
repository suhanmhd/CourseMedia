package com.course.CourseMedia.auth.service

import com.course.CourseMedia.auth.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
     val user = userRepository.findByEmail(email)
            .orElseThrow{UsernameNotFoundException("User not found")}
      return  User.withUsername(user.username)
          .password(user.passwordHash)
          .roles(user.role.name.replace("ROLE_", ""))
          .build()
    }
}
