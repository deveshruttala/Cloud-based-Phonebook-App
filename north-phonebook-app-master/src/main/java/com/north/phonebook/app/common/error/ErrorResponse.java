package com.north.phonebook.app.common.error;

/**
 * Error Response.
 */
public class ErrorResponse {

    private final String errorCode;

    private final String errorMessage;

    public ErrorResponse(final String errorCode, final String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}