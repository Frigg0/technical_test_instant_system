package com.frg.technical_test_instant_system.spring.exceptions;

import lombok.NoArgsConstructor;

/**
 * This class is to generate exception occurring while consuming external services
 */
@NoArgsConstructor
public class InstantSysException extends RuntimeException {

    public InstantSysException(String message) {
        super(message);
    }

    public InstantSysException(Throwable cause) {
        super(cause);
    }

    public InstantSysException(String message, Throwable cause) {
        super(message, cause);
    }
}
