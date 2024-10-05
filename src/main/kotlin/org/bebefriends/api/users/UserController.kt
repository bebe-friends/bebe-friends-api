package org.bebefriends.api.users

import jakarta.validation.constraints.NotNull
import org.bebefriends.core.domain.user.TermAgreement
import org.bebefriends.core.domain.user.TermAgreements
import org.bebefriends.core.domain.user.UserInfo
import org.bebefriends.core.domain.user.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/users")
class UserController(private val userService: UserService) {
    @PostMapping
    fun createUser(@AuthenticationPrincipal uid: String, @RequestBody request: CreateUserRequest) {
        userService.createUser(request.toUserInfo(), request.toTermAgreements(), uid)
    }
}
data class CreateUserRequest(@NotNull val nickname: String, @NotNull val terms: Set<TermAgreement>) {
    fun toUserInfo():UserInfo {
        return UserInfo(nickname)
    }
    fun toTermAgreements(): TermAgreements {
        return TermAgreements(terms)
    }
}