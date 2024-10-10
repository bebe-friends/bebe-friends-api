package org.bebefriends.api.common

import org.springframework.http.HttpStatus

enum class ExceptionCode(
    val message: String, val status: HttpStatus
) {
    INTERNAL_ERROR("서버 내부 오류", HttpStatus.INTERNAL_SERVER_ERROR),
    NICKNAME_DUPLICATED("중복된 닉네임입니다.", HttpStatus.CONFLICT),
    TERM_AGREEMENT_REQUEIRED("필수 약관에 동의해주세요.", HttpStatus.BAD_REQUEST);
}
