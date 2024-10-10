package org.bebefriends.api.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.bebefriends.api.common.ExceptionData
import org.bebefriends.api.common.Response
import org.springdoc.core.properties.SpringDocConfigProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import java.io.IOException

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(jsr250Enabled = true)
class SecurityConfig(
    private val objectMapper: ObjectMapper,
    private val springDocConfigProperties: SpringDocConfigProperties
) {
    @Bean
    fun securityFlterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }.cors { it.disable() }
        http.headers { it.frameOptions { options -> options.sameOrigin() } }
        http.oauth2ResourceServer {
            it.authenticationEntryPoint(this::entryPoint)
            it.accessDeniedHandler(this::accessDeniedHandler)
            it.jwt(Customizer.withDefaults())
        }
        return http.build()
    }

    @Throws(IOException::class)
    private fun entryPoint(
        request: HttpServletRequest, response: HttpServletResponse, e: RuntimeException
    ) {
        response.status = 401
        sendResponse(response, ExceptionData.of(e))
    }

    @Throws(IOException::class)
    private fun accessDeniedHandler(
        request: HttpServletRequest, response: HttpServletResponse, e: RuntimeException
    ) {
        response.status = 403
        sendResponse(response, ExceptionData.of(e))
    }

    @Throws(IOException::class)
    private fun <T> sendResponse(response: HttpServletResponse, e: T) {
        val body: Response<T> = Response(e)
        val bodyString: String = objectMapper.writeValueAsString(body)
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        response.writer.write(bodyString)
    }
}