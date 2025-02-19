package com.course.CourseMedia.auth.config



import com.course.CourseMedia.auth.security.JwtAuthFilter
import com.course.CourseMedia.auth.service.UserDetailsServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

//@Configuration
//class SecurityConfig(
//    private val userDetailsService: UserDetailsServiceImpl,
//    private val jwtAuthFilter: JwtAuthFilter
//) {
//
//    @Bean
//    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
//        http
//            .csrf { it.disable() }
//            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
//            .authorizeHttpRequests { auth ->
//                auth
//                    .requestMatchers("/api/auth/register").permitAll() // Public endpoints
//                    .requestMatchers("/admin/**").hasRole("ADMIN") // Restricted to ADMIN role
//                    .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN") // User & Admin access
//                    .anyRequest().authenticated() // Secure all other endpoints
//            }
//            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
//
//        return http.build()
//    }
//
//    @Bean
//    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
//
//    @Bean
//    fun authenticationManager(): AuthenticationManager {
//        val authProvider = DaoAuthenticationProvider()
//        authProvider.setUserDetailsService(userDetailsService)
//        authProvider.setPasswordEncoder(passwordEncoder())
//        return ProviderManager(authProvider)
//    }
//}


@Configuration
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthFilter,
    private val userDetailsService: UserDetailsService
) {

    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager {
        return authConfig.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(
                    "/api/auth/**",
                   "/auth/github/**",
                                "/v3/api-docs/**",
                                "/v3/api-docs",
                                "/swagger-ui.html/**",
                                 "/swagger-resources",
                                "/swagger-resources/**",
                                 "/configuration/ui",
                                 "/configuration/security",
                                  "/webjars/**",
                        "/swagger-ui/**"
                   ).permitAll()


                  .requestMatchers("/api/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
          .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}
//
//
//package com.course.CourseMedia.auth.config
//
//import com.course.CourseMedia.auth.security.JwtAuthFilter
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.authentication.AuthenticationManager
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.invoke
//import org.springframework.security.config.http.SessionCreationPolicy
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
//import org.springframework.security.crypto.password.PasswordEncoder
//import org.springframework.security.web.SecurityFilterChain
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
//import org.springframework.security.config.web.server.invoke
//
//@Configuration
//class SecurityConfig(
//    private val jwtAuthenticationFilter: JwtAuthFilter,
//    private val userDetailsService: UserDetailsService
//) {
//
//    @Bean
//    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager {
//        return authConfig.authenticationManager
//    }
//
//    @Bean
//    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
//
//    @Bean
//    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
//        http {
//            csrf { disable() }
//            authorizeHttpRequests {
//                authorize("/api/auth/**", permitAll)
//                authorize("/swagger-ui/**", permitAll)
//                authorize("/v3/api-docs/**", permitAll)
//                authorize("/api/admin/**", hasRole("ADMIN"))
//                authorize(anyRequest, authenticated)
//            }
//            sessionManagement { sessionCreationPolicy = SessionCreationPolicy.STATELESS }
//            addFilterBefore<UsernamePasswordAuthenticationFilter>(jwtAuthenticationFilter)
//        }
//        return http.build()
//    }
//}