package br.com.dca.exceptions;

import static java.lang.String.format;

public class CustomerAlreadyExistsException extends RuntimeException {

    public CustomerAlreadyExistsException(String customerEmail) {
        super(format("Email of customer %s already exists", customerEmail));
    }
}
