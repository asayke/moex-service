package com.asayke.moexservice.exception;

public class BondParsingException extends RuntimeException {
    public BondParsingException(Exception exception) {
        super(exception);
    }
}