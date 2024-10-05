package org.bebefriends.infra.firebase

import com.google.firebase.auth.FirebaseAuth
import org.bebefriends.core.domain.user.OAuth2Provider
import org.bebefriends.core.domain.user.OAuth2UserInfo
import org.springframework.stereotype.Service

@Service
class FirebaseUserService(private val auth: FirebaseAuth) {
    fun getInfoByUid(uid: String): OAuth2UserInfo {
        val user = auth.getUser(uid)
        return OAuth2UserInfo(uid, user.email, user.phoneNumber, OAuth2Provider.firebase)
    }
}