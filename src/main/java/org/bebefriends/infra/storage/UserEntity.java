package org.bebefriends.infra.storage;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bebefriends.core.domain.user.OAuth2UserInfo;
import org.bebefriends.core.domain.user.TermAgreement;
import org.bebefriends.core.domain.user.TermAgreements;
import org.bebefriends.core.domain.user.User;
import org.bebefriends.core.domain.user.UserAuthentication;
import org.bebefriends.core.domain.user.UserInfo;
import org.bebefriends.core.domain.user.UserRole;

@Entity(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nickname = "";
    @OneToMany(mappedBy = "pk.user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<UserRoleEntity> roles = new LinkedHashSet<>();
    @OneToMany(mappedBy = "pk.user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<OAuth2UserEntity> oAuth2Users = new LinkedHashSet<>();
    @OneToMany(mappedBy = "pk.user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<TermAgreementEntity> termAgreements = new LinkedHashSet<>();
    @OneToMany(mappedBy = "pk.user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<UserPushTokenEntity> pushTokens = new LinkedHashSet<>();
    private final TimeTable timeTable = new TimeTable();

    UserEntity(UserAuthentication identifier) {
        this.id = identifier.getId();
    }

    UserEntity(UserInfo user, TermAgreements agreements, OAuth2UserInfo oAuth2UserInfo) {
        this.nickname = user.getNickname();
        this.oAuth2Users.add(new OAuth2UserEntity(this, oAuth2UserInfo));
        this.roles.add(new UserRoleEntity(this, UserRole.PARENT));
        Set<TermAgreementEntity> agreementEntities = agreements.mapToSet(
            agreement -> new TermAgreementEntity(this, agreement));
        this.termAgreements.addAll(agreementEntities);
    }

    User toUser() {
        Set<TermAgreement> agreements = termAgreements.stream()
                                                      .map(TermAgreementEntity::toTermAgreement)
                                                      .collect(Collectors.toSet());
        Set<UserRole> roles = this.roles.stream()
                                        .map(UserRoleEntity::toUserRole)
                                        .collect(Collectors.toSet());
        return new User(new UserAuthentication(id, roles), new UserInfo(nickname), agreements);
    }

    void addFcmToken(String fcmToken) {
        pushTokens.add(new UserPushTokenEntity(this, fcmToken));
    }
}
