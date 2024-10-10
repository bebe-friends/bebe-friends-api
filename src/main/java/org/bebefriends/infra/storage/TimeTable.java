package org.bebefriends.infra.storage;

import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Embeddable
class TimeTable {
    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.MIN;
    @UpdateTimestamp
    private LocalDateTime updatedAt = LocalDateTime.MIN;
}
