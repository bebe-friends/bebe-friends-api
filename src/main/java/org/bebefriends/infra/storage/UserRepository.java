package org.bebefriends.infra.storage;

import lombok.RequiredArgsConstructor;
import org.bebefriends.core.domain.user.OAuth2UserInfo;
import org.bebefriends.core.domain.user.TermAgreements;
import org.bebefriends.core.domain.user.User;
import org.bebefriends.core.domain.user.UserAuthentication;
import org.bebefriends.core.domain.user.UserInfo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserJpaRepository jpaRepository;

    @Transactional
    public User createUser(UserInfo user, TermAgreements termAgreements,
        OAuth2UserInfo oAuth2UserInfo) {
        UserEntity userEntity = new UserEntity(user, termAgreements, oAuth2UserInfo);
        userEntity = jpaRepository.save(userEntity);
        return userEntity.toUser();
    }

    @Transactional
    public void addFcmToken(UserAuthentication identifier, String fcmToken) {
        jpaRepository.findById(identifier.getId())
                     .ifPresent((user) -> user.addFcmToken(fcmToken));
    }

    public boolean isNicknameConflict(String nickname) {
        return jpaRepository.existsByNickname(nickname);
    }
}
