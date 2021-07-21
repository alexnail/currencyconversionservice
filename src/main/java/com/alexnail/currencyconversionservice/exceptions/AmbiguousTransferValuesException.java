package com.alexnail.currencyconversionservice.exceptions;

public class AmbiguousTransferValuesException extends RuntimeException {
    public AmbiguousTransferValuesException() {
        super("Both send and receive have non-empty values. Can't decide which value to use for transfer amount calculation.");
    }
}
