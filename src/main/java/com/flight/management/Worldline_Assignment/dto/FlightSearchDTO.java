package com.flight.management.Worldline_Assignment.dto;


import jakarta.validation.constraints.NotBlank;
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
public class FlightSearchDTO {

    @NotBlank
    private String origin;
    @NotBlank
    private String destination;
    @NotBlank
    private String airline;
    @NotNull
    private LocalDateTime outboundDate;
    @NotNull
    private LocalDateTime inboundDate;

    public FlightSearchDTO(String HYD, String DEL, LocalDateTime now, LocalDateTime localDateTime) {
    }
}
