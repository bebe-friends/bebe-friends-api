package org.bebefriends.infra.storage;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bebefriends.core.domain.user.OAuth2Provider;
import org.bebefriends.core.domain.user.OAuth2UserInfo;

@Entity(name = "oauth2_users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(of = {"pk"})
class OAuth2UserEntity {
    @EmbeddedId
    private final PK pk;
    @Column(nullable = false)
    private final String email;
    @Column(nullable = false)
    private final String providerUserId;
    private final TimeTable timeTable = new TimeTable();

    private OAuth2UserEntity(UserEntity user, String email, String providerUserId, OAuth2Provider providerName) {
        this.pk = new PK(user, providerName);
        this.email = email;
        this.providerUserId = providerUserId;
    }
    OAuth2UserEntity(UserEntity user, OAuth2UserInfo info) {
        this(user, info.getEmail(), info.getId(), info.getProvider());
    }

    OAuth2UserEntity(OAuth2UserInfo info) {
        this(null, info);
    }

    @Getter
    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
    @EqualsAndHashCode
    static class PK implements Serializable {
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(nullable = false, name = "user_id")
        private final UserEntity user;
        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        private final OAuth2Provider providerName;

        PK(UserEntity user, OAuth2Provider providerName) {
            this.user = user;
            this.providerName = providerName;
        }
    }
}
