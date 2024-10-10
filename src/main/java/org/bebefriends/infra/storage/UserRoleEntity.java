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
import org.bebefriends.core.domain.user.UserRole;

@Entity(name = "user_roles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
class UserRoleEntity {
    @EmbeddedId
    private final PK pk;

    UserRoleEntity(UserEntity user, UserRole role) {
        pk = new PK(user, role);
    }

    @Getter
    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
    @EqualsAndHashCode
    static class PK implements Serializable {
        @JoinColumn(nullable = false, name = "user_id")
        @ManyToOne(fetch = FetchType.LAZY)
        private final UserEntity user;
        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private final UserRole role;

        protected PK(UserEntity user, UserRole role) {
            this.user = user;
            this.role = role;
        }
    }

    UserRole toUserRole() {
        assert pk != null;
        return pk.role;
    }
}
