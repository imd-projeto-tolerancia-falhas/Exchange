package com.exchange;

public class FailSimulatedException extends RuntimeException {
    public FailSimulatedException(String message) {
        super(message);
    }
}