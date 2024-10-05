package org.bebefriends.core.domain.user

data class OAuth2UserInfo(val id: String, val email: String, val phoneNumber: String, val provider: OAuth2Provider)
