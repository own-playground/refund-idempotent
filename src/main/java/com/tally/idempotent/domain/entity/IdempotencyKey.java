package com.tally.idempotent.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
public class IdempotencyKey {

    @Id
    @GeneratedValue(generator = "uuid2")
    private String key;

    @Column(columnDefinition = "TEXT")
    private String responseBody;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime expiredAt;

    protected IdempotencyKey() {}

    @Builder
    private IdempotencyKey(
            final String key,
            final String responseBody,
            final LocalDateTime expiredAt
    ) {
        this.key = key;
        this.responseBody = responseBody;
        this.createdAt = LocalDateTime.now();
        this.expiredAt = expiredAt;
    }

    public static IdempotencyKey of(final String key, final String responseBody, final LocalDateTime expiredAt) {
        return IdempotencyKey.builder()
                .key(key)
                .responseBody(responseBody)
                .expiredAt(expiredAt)
                .build();
    }
}
