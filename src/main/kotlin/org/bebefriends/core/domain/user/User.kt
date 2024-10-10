package org.bebefriends.core.domain.user

class User(val id: UserAuthentication, val userInfo: UserInfo, val termAgreements: Collection<TermAgreement>)
data class UserInfo(var nickname: String)
data class UserAuthentication(val id: Long, val roles: Collection<UserRole>)

enum class UserRole {
    PARENT, ADMIN
}