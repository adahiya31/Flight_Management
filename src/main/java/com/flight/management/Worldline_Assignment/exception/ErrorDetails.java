package com.flight.management.Worldline_Assignment.exception;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorDetails {
    private int status;
    private String message;
    private long timestamp;
}
