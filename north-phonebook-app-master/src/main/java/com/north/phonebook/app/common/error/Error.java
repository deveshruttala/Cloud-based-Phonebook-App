package com.north.phonebook.app.common.error;

/**
 * Error Enum Type.
 */
public enum Error {

    INVALID_PAYLOAD_VALIDATION("INVALID_PAYLOAD");

    private final String errorCode;

    Error(final String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
