package org.bebefriends.api.common

data class Response<T>(val success: Boolean, val body: T) {
    constructor(body: T) : this(body !is ExceptionData, body)
}
