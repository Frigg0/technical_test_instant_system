package com.frg.technical_test_instant_system.configuration;

import com.frg.technical_test_instant_system.spring.exceptions.InstantSysException;
import com.frg.technical_test_instant_system.spring.exceptions.InstantSysTechnicalException;
import lombok.extern.slf4j.Slf4j;

/**
 * Abstract class for configuration classes
 */
@Slf4j
public abstract class AbstractExceptionConfiguration {

    // InstanceSysException
    /**
     * Create and return an InstantSysException
     * Allows to log the error before throwing it.
     * @return a new InstantSysException
     */
    protected InstantSysException instantSysException() {

        // Create exception
        InstantSysException exception = new InstantSysException();
        // Log it
        log.error("Error provider exception thrown", exception);

        return exception;
    }

    /**
     * Create and return an InstantSysException
     * Allows to log the error before throwing it.
     * @param message error message
     * @return a new InstantSysException with a message
     */
    protected InstantSysException instantSysException(String message) {

        // Create exception
        InstantSysException exception = new InstantSysException(message);
        // Log it
        log.error(String.format("Error provider exception thrown with message: %s", exception.getMessage()));

        return exception;
    }

    /**
     * Create and return an InstantSysException
     * Allows to log the error before throwing it.
     * @param cause entire exception
     * @return a new InstantSysException with the all exception on
     */
    protected InstantSysException instantSysException(Throwable cause) {

        // Create exception
        InstantSysException exception = new InstantSysException();
        // Log it
        log.error("Error provider exception thrown", exception);

        return exception;
    }

    /**
     * Create and return an InstantSysException
     * Allows to log the error before throwing it.
     * @param message error message
     * @param cause entire exception
     * @return a new InstantSysException with a message and the all exception
     */
    protected InstantSysException instantSysException(String message, Throwable cause) {

        // Create exception
        InstantSysException exception = new InstantSysException();
        // Log it
        log.error("Error provider exception thrown", exception);

        return exception;
    }

    // InstantSysTechnicalException
    /**
     * Create and return an InstantSysTechnicalException
     * Allows to log the error before throwing it.
     * @return a new InstantSysTechnicalException
     */
    protected InstantSysTechnicalException instantTechnicalSysException() {

        // Create exception
        InstantSysTechnicalException exception = new InstantSysTechnicalException();
        // Log it
        log.error("Error internal exception thrown", exception);

        return exception;
    }

    /**
     * Create and return an InstantSysTechnicalException
     * Allows to log the error before throwing it.
     * @param message error message
     * @return a new InstantSysTechnicalException with a message
     */
    protected InstantSysTechnicalException instantTechnicalSysException(String message) {

        // Create exception
        InstantSysTechnicalException exception = new InstantSysTechnicalException(message);
        // Log it
        log.error(String.format("Error internal exception thrown with message: %s", exception.getMessage()));

        return exception;
    }

    /**
     * Create and return an InstantSysTechnicalException
     * Allows to log the error before throwing it.
     * @param cause entire exception
     * @return a new InstantSysTechnicalException with the all exception on
     */
    protected InstantSysTechnicalException instantTechnicalSysException(Throwable cause) {

        // Create exception
        InstantSysTechnicalException exception = new InstantSysTechnicalException();
        // Log it
        log.error("Error internal exception thrown", exception);

        return exception;
    }

    /**
     * Create and return an InstantSysTechnicalException
     * Allows to log the error before throwing it.
     * @param message error message
     * @param cause entire exception
     * @return a new InstantSysTechnicalException with a message and the all exception
     */
    protected InstantSysTechnicalException instantTechnicalSysException(String message, Throwable cause) {

        // Create exception
        InstantSysTechnicalException exception = new InstantSysTechnicalException();
        // Log it
        log.error("Error internal exception thrown", exception);

        return exception;
    }
}
