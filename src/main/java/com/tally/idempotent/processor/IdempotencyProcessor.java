package com.tally.idempotent.processor;

import com.tally.idempotent.domain.entity.IdempotencyKey;

public interface IdempotencyProcessor {

    void save(final String key, final String result, final long ttl);

    IdempotencyKey read(final String key);

}
