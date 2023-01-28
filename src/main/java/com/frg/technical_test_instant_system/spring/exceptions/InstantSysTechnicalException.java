package com.frg.technical_test_instant_system.spring.exceptions;

import lombok.NoArgsConstructor;

/**
 * This class is to generate exception occurring while using internal services
 */
@NoArgsConstructor
public class InstantSysTechnicalException extends RuntimeException {


    public InstantSysTechnicalException(String message) {
        super(message);
    }

    public InstantSysTechnicalException(Throwable cause) {
        super(cause);
    }

    public InstantSysTechnicalException(String message, Throwable cause) {
        super(message, cause);
    }
}
