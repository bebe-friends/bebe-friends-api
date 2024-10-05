package org.bebefriends.core.domain.user

import jakarta.transaction.Transactional
import org.bebefriends.api.common.CustomException
import org.bebefriends.api.common.ExceptionCode
import org.bebefriends.infra.firebase.FirebaseUserService
import org.bebefriends.infra.storage.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository, private val firebaseUserService: FirebaseUserService
) {
    @Transactional
    fun createUser(userInfo: UserInfo, termAgreements: TermAgreements, oAuth2Uid:String) {
        validateNickname(userInfo.nickname)
        val info = firebaseUserService.getInfoByUid(oAuth2Uid)
        userRepository.createUser(userInfo, termAgreements, info)
    }

    fun validateNickname(nickname:String) {
        if(userRepository.isNicknameConflict(nickname)) {
            throw CustomException(ExceptionCode.NICKNAME_DUPLICATED)
        }
    }

    fun addFcmToken(identifier: UserAuthentication, fcmToken: String) {
        userRepository.addFcmToken(identifier, fcmToken)
    }
}