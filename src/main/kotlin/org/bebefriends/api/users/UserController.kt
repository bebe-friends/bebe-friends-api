package org.bebefriends.api.users

import jakarta.validation.constraints.NotNull
import org.bebefriends.core.domain.user.TermAgreement
import org.bebefriends.core.domain.user.TermAgreements
import org.bebefriends.core.domain.user.UserInfo
import org.bebefriends.core.domain.user.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/users")
class UserController(private val userService: UserService) {
    @PostMapping
    fun createUser(@AuthenticationPrincipal uid: String, @RequestBody request: CreateUserRequestBody) {
        userService.createUser(request.toUserInfo(), request.toTermAgreements(), uid)
    }

    @GetMapping("check")
    fun checkFormData(query: CheckUserDataQuery) {
        if (query.nickname != null) {
            userService.validateNickname(query.nickname)
        }
    }
}
data class CreateUserRequestBody(@NotNull val nickname: String, @NotNull val terms: Set<TermAgreement>) {
    fun toUserInfo():UserInfo {
        return UserInfo(nickname)
    }
    fun toTermAgreements(): TermAgreements {
        return TermAgreements(terms)
    }
}

data class CheckUserDataQuery(val nickname: String?)