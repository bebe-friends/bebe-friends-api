package org.bebefriends.infra.storage;

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
import org.bebefriends.core.domain.user.Term;
import org.bebefriends.core.domain.user.TermAgreement;

@Entity(name = "term_agreements")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode(of = {"pk"})
class TermAgreementEntity {

    @EmbeddedId
    private final PK pk;
    private boolean agreed;
    private final TimeTable timeTable = new TimeTable();

    TermAgreementEntity(UserEntity user, TermAgreement agreement) {
        this.pk = new PK(user, agreement.getTerm());
        this.agreed = agreement.getAgreed();
    }

    TermAgreementEntity(TermAgreement agreement) {
        this(null, agreement);
    }

    @Getter
    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
    @EqualsAndHashCode
    static class PK implements Serializable {

        @JoinColumn(name = "user_id")
        @ManyToOne(fetch = FetchType.LAZY)
        private final UserEntity user;
        @Enumerated(EnumType.STRING)
        private final Term term;

        PK(UserEntity user, Term term) {
            this.user = user;
            this.term = term;
        }
    }
    TermAgreement toTermAgreement() {
        return new TermAgreement(pk.term, agreed);
    }
}
