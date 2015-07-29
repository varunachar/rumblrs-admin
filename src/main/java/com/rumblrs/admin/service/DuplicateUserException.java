/**
 * 
 */
package com.rumblrs.admin.service;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown when an attempt is made to create a duplicate user
 * @author Varun Achar
 *
 */
public class DuplicateUserException extends AuthenticationException {

    public DuplicateUserException(String message) {
        super(message);
    }

    public DuplicateUserException(String message, Throwable t) {
        super(message, t);
    }
}
