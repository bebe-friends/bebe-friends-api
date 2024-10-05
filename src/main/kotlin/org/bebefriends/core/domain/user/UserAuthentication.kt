package org.bebefriends.core.domain.user

data class UserAuthentication(val id: Long, val roles: Collection<UserRole>)
