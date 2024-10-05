package org.bebefriends.infra.storage;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "user_push_tokens")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(of = {"pk"})
class UserPushTokenEntity {

    @EmbeddedId
    private final PK pk;
    private final TimeTable timeTable = new TimeTable();

    UserPushTokenEntity(UserEntity user, String fcmToken) {
        this.pk = new PK(user, fcmToken);
    }

    @Getter
    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
    @EqualsAndHashCode
    static class PK implements Serializable {

        @JoinColumn(name = "user_id", nullable = false)
        @ManyToOne(fetch = FetchType.LAZY)
        private final UserEntity user;
        @Column(nullable = false)
        private final String fcmToken;

        PK(UserEntity user, String fcmToken) {
            this.user = user;
            this.fcmToken = fcmToken;
        }
    }
}
