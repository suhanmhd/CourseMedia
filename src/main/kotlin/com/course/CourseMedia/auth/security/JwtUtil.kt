package com.course.CourseMedia.auth.security


import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtUtil {
    @Value("\${JWT_KEY}")
    lateinit var secretKey: String


    private val accessTokenValidity = 1000 * 60 * 15 // 15 minutes
    private val refreshTokenValidity = 1000 * 60 * 60 * 24 * 7 // 7 days

    private fun getSigningKey(): Key {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun extractUsername(token: String): String {
        return extractClaim(token) { it.subject }
    }

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .body
        return claimsResolver(claims)
    }

    fun generateAccessToken(userDetails: UserDetails): String {
        return Jwts.builder()
            .setSubject(userDetails.username)
            .claim("role", userDetails.authorities.first().authority)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() +accessTokenValidity)) // 1 hour
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    fun generateRefreshToken(userDetails: UserDetails): String {
        return Jwts.builder()
            .setSubject(userDetails.username)
            .claim("role", userDetails.authorities.first().authority)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() +refreshTokenValidity)) // 1 hour
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact()
    }


    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    fun isTokenExpired(token: String): Boolean {
        return extractClaim(token) { it.expiration }.before(Date())
    }
}
