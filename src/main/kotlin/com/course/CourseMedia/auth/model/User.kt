package com.course.CourseMedia.auth.model


import com.course.CourseMedia.auth.enum.Role
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column( nullable = false)
    val name: String,

    @Column(unique = true, nullable = false)
    val email: String,

    @Column(nullable = false)
    val passwordHash: String,

    @Enumerated(EnumType.STRING)
    val role: Role
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(GrantedAuthority { "ROLE_$role" })
    }

    override fun getPassword(): String = passwordHash
    override fun getUsername(): String = email
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}
