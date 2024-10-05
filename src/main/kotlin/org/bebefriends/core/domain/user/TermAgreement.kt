package org.bebefriends.core.domain.user

import org.bebefriends.api.common.CustomException
import org.bebefriends.api.common.ExceptionCode


class TermAgreements(private val agreements: Set<TermAgreement>) {
    fun validate() {
        agreements.forEach{it.validate()}
    }
    fun<T> mapToSet(mapper: (TermAgreement) -> T): LinkedHashSet<T> {
        return agreements.mapTo(LinkedHashSet(), mapper)
    }
}
data class TermAgreement(val term: Term, val agreed: Boolean = false) {
    fun validate() {
        if (term.required && !agreed) {
            throw CustomException(ExceptionCode.TERM_AGREEMENT_REQUEIRED)
        }
    }
}
enum class Term(val required: Boolean) {
    USER_PERSONAL_INFORMATION(true),
    SERVICE_USING(required = true),
    GREATER_THAN_OR_EQUAL_TO_14(required = true),
    AD_EVENT_PUSH_RECEIVING(required = false),
    NIGHT_TIME_PUSH_RECEIVING(required = false);
}