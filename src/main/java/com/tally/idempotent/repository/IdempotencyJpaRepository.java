package com.tally.idempotent.repository;

import com.tally.idempotent.domain.entity.IdempotencyKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface IdempotencyJpaRepository extends JpaRepository<IdempotencyKey, String> {

    Optional<IdempotencyKey> findByKey(@Param("key") String key);

    void deleteByExpiredAtBefore(LocalDateTime now);

}
