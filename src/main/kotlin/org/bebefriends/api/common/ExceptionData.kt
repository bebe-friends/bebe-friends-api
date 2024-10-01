package org.bebefriends.api.common

data class ExceptionData(val code: String, val message: String) {
    companion object {
        fun of(e: CustomException): ExceptionData {
            return ExceptionData(e.code, e.localizedMessage)
        }
        fun of(e: RuntimeException): ExceptionData {
            return ExceptionData(ExceptionCode.INTERNAL_ERROR.name, e.localizedMessage)
        }
    }
}
