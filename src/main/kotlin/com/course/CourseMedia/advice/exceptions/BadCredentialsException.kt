package com.course.CourseMedia.advice.exceptions

class BadCredentialsException(message: String) : RuntimeException(message)
class AuthenticationException(message: String) : RuntimeException(message)