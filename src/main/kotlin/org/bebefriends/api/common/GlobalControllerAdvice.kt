package org.bebefriends.api.common

import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
@Slf4j
class GlobalControllerAdvice {
    val logger: Logger = LoggerFactory.getLogger(GlobalControllerAdvice::class.java)

    @ExceptionHandler(CustomException::class)
    fun catchCustomException(e: CustomException) {
        logger.info(e.code + e.localizedMessage)
        logger.info(e.stackTraceToString())
        val data = ExceptionData.of(e)
        ResponseEntity.status(e.status).body(Response(data))
    }

    @ExceptionHandler(RuntimeException::class)
    fun catchRuntimeException(e: RuntimeException) {
        logger.error(e.localizedMessage)
        logger.error(e.stackTraceToString())
        val data = ExceptionData.of(e)
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response(data))
    }
}