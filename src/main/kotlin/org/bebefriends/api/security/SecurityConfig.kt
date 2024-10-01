package org.bebefriends.api.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.bebefriends.api.common.ExceptionData
import org.bebefriends.api.common.Response
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import java.io.IOException

@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
class SecurityConfig(private val objectMapper: ObjectMapper) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }.cors { it.disable() }
        http.oauth2ResourceServer { resource ->
            resource.authenticationEntryPoint(this::entryPoint)
            resource.accessDeniedHandler(this::accessDeniedHandler)
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