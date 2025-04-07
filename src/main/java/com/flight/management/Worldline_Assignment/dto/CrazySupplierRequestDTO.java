package com.flight.management.Worldline_Assignment.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrazySupplierRequestDTO {

    @NotNull
    private String from;
    @NotNull
    private String to;
    @NotNull(message = "Outbound date must not be null.")
    @Future(message = "Outbound date must be in the future.")
    private LocalDateTime outboundDate;
    @NotNull(message = "Inbound date must not be null.")
    @Future(message = "Inbound date must be in the future.")
    private LocalDateTime inboundDate;

}