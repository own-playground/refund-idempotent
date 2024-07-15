package com.tally.idempotent.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {

    /**
     * Prefix for idempotent item in store
     */
    String key() default "";

    /**
     * Ttl for idempotent item in store in seconds
     */

    long ttl() default 0;
}
