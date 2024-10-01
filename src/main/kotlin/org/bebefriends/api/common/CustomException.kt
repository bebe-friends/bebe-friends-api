package org.bebefriends.api.common

import org.springframework.http.HttpStatus

/**
 * ControllerAdvice에서 잡을 수 있도록 예외코드를 포함한 예외클래스 추가
 */
class CustomException(code: ExceptionCode) : RuntimeException(code.message) {
    val code = code.name
    val status: HttpStatus = code.status
}
