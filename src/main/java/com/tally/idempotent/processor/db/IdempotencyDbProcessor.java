package com.tally.idempotent.processor.db;

import com.tally.idempotent.domain.entity.IdempotencyKey;
import com.tally.idempotent.processor.IdempotencyProcessor;
import com.tally.idempotent.repository.IdempotencyJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class IdempotencyDbProcessor implements IdempotencyProcessor {

    private final IdempotencyJpaRepository idempotencyJpaRepository;

    @Override
    @Transactional
    public void save(final String key, final String result, final long ttl) {
        final IdempotencyKey idempotencyKey = IdempotencyKey.of(key, result, LocalDateTime.now().plusSeconds(ttl));
        idempotencyJpaRepository.save(idempotencyKey);
    }

    @Override
    public IdempotencyKey read(final String key) {
        return idempotencyJpaRepository.findByKey(key).orElse(null);
    }

    @Transactional
    @Scheduled(fixedRate = 60 * 60 * 1000) // 1시간마다 만료된 레코드 제거
    public void deleteExpiredKeys() {
        LocalDateTime now = LocalDateTime.now();
        idempotencyJpaRepository.deleteByExpiredAtBefore(now);
    }
}
