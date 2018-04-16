package com.sirma.employees.exception;

public class EmployeePairNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -619991220438254553L;

    public EmployeePairNotFoundException(String message) {
        super(message);
    }
}
