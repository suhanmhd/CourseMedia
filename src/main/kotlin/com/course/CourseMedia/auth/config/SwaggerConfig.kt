package com.course.CourseMedia.auth.config


import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("EduMedia API")
                    .description("API documentation for the CourseMedia project")
                    .version("1.0.0")
            )
    }
}
