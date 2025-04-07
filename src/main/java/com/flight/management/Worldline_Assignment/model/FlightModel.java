package com.flight.management.Worldline_Assignment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Airline is required")
    private String airline;

    @NotBlank(message = "Supplier is required")
    private String supplier;

    @Column(nullable = false)
    @Positive(message = "Fare must be positive")
    @NotNull(message = "Fare is required")
    private Double fare;

    @Column(name = "departure_airport", nullable = false, length = 3)
    @NotNull
    @Size(min = 3, max = 3, message = "Departure airport must be a 3-letter code")
    private String departureAirport;

    @Column(name = "destination_airport", nullable = false, length = 3)
    @NotBlank(message = "Destination airport is required")
    @Size(min = 3, max = 3, message = "Destination airport must be 3 characters")
    private String destinationAirport;

    @Column(name = "departure_time", nullable = false)
    @Future(message = "Departure time must be in the future")
    @NotNull(message = "Departure time is required")
    private LocalDateTime departureTime;

    @Column(name = "arrival_time", nullable = false)
    @Future(message = "Arrival time must be in the future")
    @NotNull(message = "Arrival time is required")
    private LocalDateTime arrivalTime;
}