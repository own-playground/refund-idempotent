package com.tally.idempotent.processor.db;

import com.tally.idempotent.domain.entity.IdempotencyKey;
import com.tally.idempotent.processor.IdempotencyProcessor;
import com.tally.idempotent.repository.IdempotencyJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class IdempotencyDbProcessor implements IdempotencyProcessor {

    private final IdempotencyJpaRepository idempotencyJpaRepository;

    @Override
    public void save(final String key, final String result, final long ttl) {
        final IdempotencyKey idempotencyKey = IdempotencyKey.of(key, result, LocalDateTime.now().plusSeconds(ttl));
        idempotencyJpaRepository.save(idempotencyKey);
    }

    @Override
    public IdempotencyKey read(final String key) {
        return idempotencyJpaRepository.findByKey(key)
                .orElseThrow(() -> new IllegalArgumentException("Idempotency key not found"));
    }
}
