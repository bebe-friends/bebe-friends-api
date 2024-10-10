package org.bebefriends.infra.storage;

import org.bebefriends.core.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByNickname(String nickname);
    @Query("select user from oauth2_users o left join o.pk.user user where o.providerUserId = :uid and o.pk.providerName = org.bebefriends.core.domain.user.OAuth2Provider.firebase")
    User findByFirebaseUid(String uid);
}
