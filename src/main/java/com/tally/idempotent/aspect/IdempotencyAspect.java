package com.tally.idempotent.aspect;

import com.tally.idempotent.annotation.Idempotent;
import com.tally.idempotent.domain.entity.IdempotencyKey;
import com.tally.idempotent.mapper.JsonMapper;
import com.tally.idempotent.processor.IdempotencyProcessor;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class IdempotencyAspect {

    private static final String IDEMPOTENT_KEY_HEADER = "X-Idempotency-Key";

    private final IdempotencyProcessor idempotencyProcessor;

    @Around("@annotation(com.tally.idempotent.annotation.Idempotent)")
    public Object idempotentOperation(final ProceedingJoinPoint joinPoint) {

        final Idempotent annotation = getAnnotation(joinPoint);
        final String key = getIdempotentKey();

        if (key == null) {
            throw new IllegalArgumentException("Idempotency key is required");
        }

        final IdempotencyKey idempotencyKey = idempotencyProcessor.read(key);

        if(idempotencyKey != null) {
            return JsonMapper.readFromJson(idempotencyKey.getResponseBody(), getReturnType(joinPoint));
        }

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        idempotencyProcessor.save(key, JsonMapper.writeToJson(result), annotation.ttl());

        return result;
    }

    private Idempotent getAnnotation(final ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod().getAnnotation(Idempotent.class);
    }

    private String getIdempotentKey() {
        final ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest().getHeader(IDEMPOTENT_KEY_HEADER) : null;
    }

    private Class<?> getReturnType(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getReturnType();
    }


}