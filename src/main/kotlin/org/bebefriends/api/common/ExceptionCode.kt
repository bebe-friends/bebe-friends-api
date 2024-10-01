package org.bebefriends.api.common

import org.springframework.http.HttpStatus

enum class ExceptionCode(
    val message: String, val status: HttpStatus
) {
    INTERNAL_ERROR("서버 내부 오류", HttpStatus.INTERNAL_SERVER_ERROR);
}
